package com.sangmeebee.searchmovie.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.databinding.ActivityMainBinding
import com.sangmeebee.searchmovie.domain.util.EmptyQueryException
import com.sangmeebee.searchmovie.util.SoftInputUtil
import com.sangmeebee.searchmovie.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.io.IOException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val movieAdapter: MovieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mainViewModel
        }
        setContentView(binding.root)
        setRecyclerView()
        setRefreshListener()

        observePagingRefresh()
        observePagingAppend()
        observeMovies()
    }

    private fun setRecyclerView() {
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.adapter = movieAdapter.apply {
            addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && movieAdapter.itemCount != 0) {
                    SoftInputUtil(this@MainActivity).hideKeyboard(binding.etQuery)
                }
            }
        }.withLoadStateFooter(MovieLoadStateAdapter(movieAdapter::retry))
    }

    private fun setRefreshListener() {
        binding.srlLoading.setOnRefreshListener {
            movieAdapter.refresh()
        }
    }

    private fun observeMovies() = repeatOnStarted {
        mainViewModel.movies.collectLatest {
            movieAdapter.submitData(it)
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
            is EmptyQueryException -> showToast(resources.getString(R.string.movie_list_empty_query))
            is IOException -> showToast(resources.getString(R.string.all_network_disconnect))
            else -> showToast(throwable.message)
        }
    }

    private fun showToast(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}