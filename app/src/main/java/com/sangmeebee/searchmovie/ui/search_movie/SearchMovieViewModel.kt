package com.sangmeebee.searchmovie.ui.search_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.usecase.BookmarkMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetAllBookmarkedMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.UnbookmarkMovieUseCase
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import com.sangmeebee.searchmovie.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getAllBookmarkedMovieUseCase: GetAllBookmarkedMovieUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
) : ViewModel() {

    private val query = MutableEventFlow<String>()

    private val bookmarkedMovies: MutableList<String> = mutableListOf()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<MovieModel>> =
        query.flatMapLatest {
            fetchMovie(it).map { pagingData: PagingData<Movie> ->
                pagingData.map { movieInfo ->
                    val isBookmarked = bookmarkedMovies.contains(movieInfo.link)
                    movieInfo.toPresentation(
                        isBookmarked = isBookmarked,
                        bookmark = { bookmarkMovie(isBookmarked, movieInfo.link) }
                    )
                }
            }
        }.cachedIn(viewModelScope)

    fun handleQuery(newQuery: String) = viewModelScope.launch {
        fetchBookmarkedMovies()
        query.emit(newQuery)
    }

    private fun fetchMovie(query: String): Flow<PagingData<Movie>> {
        return getMovieUseCase(query)
    }

    fun fetchBookmarkedMovies() = viewModelScope.launch {
        bookmarkedMovies.clear()
        bookmarkedMovies.addAll(getAllBookmarkedMovieUseCase())
    }

    private fun bookmarkMovie(isBookmarked: Boolean, movieId: String) = viewModelScope.launch {
        if (isBookmarked) {
            unbookmarkMovieUseCase(movieId)
        } else {
            bookmarkMovieUseCase(movieId)
        }
    }
}