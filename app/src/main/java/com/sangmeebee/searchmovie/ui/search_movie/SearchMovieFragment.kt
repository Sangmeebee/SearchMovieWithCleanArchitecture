package com.sangmeebee.searchmovie.ui.search_movie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.databinding.FragmentSearchMovieBinding
import com.sangmeebee.searchmovie.domain.util.*
import com.sangmeebee.searchmovie.ui.adapter.MovieAdapter
import com.sangmeebee.searchmovie.ui.adapter.MovieLoadStateAdapter
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.SoftInputUtil
import com.sangmeebee.searchmovie.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMovieFragment :
    BaseFragment<FragmentSearchMovieBinding>(FragmentSearchMovieBinding::inflate) {

    private val searchMovieViewModel by viewModels<SearchMovieViewModel>()
    private val movieAdapter: MovieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeMovies()
        observeErrorEvent()
        observePagingAppend()
        observePagingRefresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setSwipeRefreshLayout()
    }

    override fun FragmentSearchMovieBinding.setBinding() {
        lifecycleOwner = this@SearchMovieFragment.viewLifecycleOwner
        viewModel = searchMovieViewModel
    }

    private fun setRecyclerView() {
        binding.rvMovieList.apply {
            setHasFixedSize(true)
            adapter = movieAdapter.apply {
                addLoadStateListener { loadState ->
                    if (loadState.source.refresh is LoadState.NotLoading && movieAdapter.itemCount != 0) {
                        SoftInputUtil(requireContext()).hideKeyboard(binding.etQuery)
                    }
                }
            }.withLoadStateFooter(MovieLoadStateAdapter(movieAdapter::retry))
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.apply {
            isEnabled = false
            setOnRefreshListener {
                movieAdapter.refresh()
            }
        }
    }

    private fun observeMovies() = repeatOnStarted {
        searchMovieViewModel.movies.collectLatest {
            movieAdapter.submitData(it)
        }
    }

    private fun observeErrorEvent() = repeatOnStarted {
        searchMovieViewModel.errorEvent.collectLatest { throwable ->
            when (throwable) {
                is GetBookmarkException -> showToast(resources.getString(R.string.search_movie_get_bookmark_error))
                is BookmarkException -> showToast(resources.getString(R.string.search_movie_bookmark_error))
                is UnBookmarkException -> showToast(resources.getString(R.string.search_movie_unbookmark_error))
            }
        }
    }

    private fun observePagingRefresh() = lifecycleScope.launch {
        movieAdapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .collectLatest { loadStates ->
                checkPagingRefreshLoadState(loadStates.refresh)
            }
    }

    private fun checkPagingRefreshLoadState(refreshLoadState: LoadState) {
        when (refreshLoadState) {
            is LoadState.Loading -> binding.srlLoading.isRefreshing = true
            is LoadState.Error -> {
                binding.srlLoading.isRefreshing = false
                checkErrorState(refreshLoadState.error)
            }
            is LoadState.NotLoading -> {
                binding.rvMovieList.scrollToPosition(0)
                binding.srlLoading.isEnabled = true
                binding.srlLoading.isRefreshing = false
            }
        }
    }

    private fun observePagingAppend() = lifecycleScope.launch {
        movieAdapter.loadStateFlow
            .distinctUntilChangedBy { it.append }
            .filter { it.append is LoadState.Error }
            .collectLatest {
                checkErrorState((it.append as LoadState.Error).error)
            }
    }

    private fun checkErrorState(throwable: Throwable) {
        when (throwable) {
            is EmptyQueryException -> showToast(resources.getString(R.string.search_movie_empty_query))
            is HttpConnectionException -> showToast(resources.getString(R.string.all_network_disconnect))
            else -> showToast(throwable.message)
        }
    }

    private fun showToast(message: String?) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}