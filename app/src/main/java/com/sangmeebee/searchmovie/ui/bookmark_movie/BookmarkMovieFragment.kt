package com.sangmeebee.searchmovie.ui.bookmark_movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sangmeebee.searchmovie.databinding.FragmentBookmarkMovieBinding
import com.sangmeebee.searchmovie.ui.adapter.BookmarkMovieAdapter
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.ui.search_movie.SearchMovieViewModel
import com.sangmeebee.searchmovie.util.repeatOnStarted

class BookmarkMovieFragment :
    BaseFragment<FragmentBookmarkMovieBinding>(FragmentBookmarkMovieBinding::inflate) {

    private val searchMovieViewModel by activityViewModels<SearchMovieViewModel>()
    private val movieAdapter: BookmarkMovieAdapter by lazy {
        BookmarkMovieAdapter(
            bookmark = searchMovieViewModel::bookmarkMovie,
            navigateToDetailFragment = ::navigateToDetailFragment
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeBookmarkedMovieState()
    }

    private fun setRecyclerView() {
        binding.rvMovieList.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun observeBookmarkedMovieState() = repeatOnStarted {
        searchMovieViewModel.bookmarkedMovieState.collect {
            movieAdapter.submitList(it)
        }
    }

    private fun navigateToDetailFragment(link: String) {
        val action =
            BookmarkMovieFragmentDirections.actionBookmarkMovieFragmentToDetailMovieFragment(link)
        findNavController().navigate(action)
    }
}