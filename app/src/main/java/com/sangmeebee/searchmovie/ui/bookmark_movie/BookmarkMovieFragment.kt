package com.sangmeebee.searchmovie.ui.bookmark_movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.cache.util.BookmarkException
import com.sangmeebee.searchmovie.cache.util.GetBookmarkException
import com.sangmeebee.searchmovie.cache.util.UnBookmarkException
import com.sangmeebee.searchmovie.databinding.FragmentBookmarkMovieBinding
import com.sangmeebee.searchmovie.ui.MainActivity
import com.sangmeebee.searchmovie.ui.adapter.BookmarkMovieAdapter
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class BookmarkMovieFragment :
    BaseFragment<FragmentBookmarkMovieBinding>(FragmentBookmarkMovieBinding::inflate) {

    private val bookmarkMovieViewModel by viewModels<BookmarkMovieViewModel>()
    private val movieAdapter: BookmarkMovieAdapter by lazy {
        BookmarkMovieAdapter(
            bookmark = bookmarkMovieViewModel::fetchBookmark,
            navigateToDetailFragment = ::navigateToDetailFragment
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setUpObserveUiState()
    }

    private fun setRecyclerView() {
        binding.rvMovieList.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setUpObserveUiState() {
        observeError()
        observeMovies()
        observeIsNeedToLogin()
    }

    private fun observeMovies() = repeatOnStarted {
        bookmarkMovieViewModel.bookmarkedMovies.collectLatest { movies ->
            movieAdapter.submitList(movies)
        }
    }

    private fun observeError() = repeatOnStarted {
        bookmarkMovieViewModel.uiState.map { it.error }.distinctUntilChanged().collectLatest { throwable ->
            if (throwable != null) {
                when (throwable) {
                    is GetBookmarkException -> showToast(resources.getString(R.string.search_movie_get_bookmark_error))
                    is BookmarkException -> showToast(resources.getString(R.string.search_movie_bookmark_error))
                    is UnBookmarkException -> showToast(resources.getString(R.string.search_movie_unbookmark_error))
                }
                bookmarkMovieViewModel.fetchError(null)
            }
        }
    }

    private fun observeIsNeedToLogin() = repeatOnStarted {
        bookmarkMovieViewModel.uiState.map { it.IsNeedToLogin }.distinctUntilChanged().collectLatest { isNeedToLogin ->
            if (isNeedToLogin) {
                (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_nav).selectedItemId = R.id.my
                bookmarkMovieViewModel.fetchIsNeedToLogin()
            }
        }
    }

    private fun navigateToDetailFragment(link: String) {
        val action =
            BookmarkMovieFragmentDirections.actionBookmarkMovieFragmentToDetailMovieFragment(link)
        findNavController().navigate(action)
    }
}