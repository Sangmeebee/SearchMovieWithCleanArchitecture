package com.sangmeebee.searchmovie.ui.bookmark_movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sangmeebee.searchmovie.databinding.FragmentBookmarkMovieBinding
import com.sangmeebee.searchmovie.ui.adapter.BookmarkMovieAdapter
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.ui.search_movie.SearchMovieViewModel
import com.sangmeebee.searchmovie.util.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest

class BookmarkMovieFragment :
    BaseFragment<FragmentBookmarkMovieBinding>(FragmentBookmarkMovieBinding::inflate) {

    private val searchMovieViewModel by activityViewModels<SearchMovieViewModel>()
    private val movieAdapter: BookmarkMovieAdapter = BookmarkMovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setSwipeRefreshLayout()

        observeBookmarkedMovieState()
    }

    private fun setRecyclerView() {
        binding.rvMovieList.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.isEnabled = false
    }

    private fun observeBookmarkedMovieState() = repeatOnStarted {
        searchMovieViewModel.bookmarkedMovieState.collectLatest {
            movieAdapter.submitList(it)
        }
    }
}