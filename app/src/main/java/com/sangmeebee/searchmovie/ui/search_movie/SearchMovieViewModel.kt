package com.sangmeebee.searchmovie.ui.search_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import com.sangmeebee.searchmovie.domain.usecase.BookmarkMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetAllBookmarkedMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.UnbookmarkMovieUseCase
import com.sangmeebee.searchmovie.domain.util.BookmarkException
import com.sangmeebee.searchmovie.domain.util.UnBookmarkException
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.mapper.toMovieBookmark
import com.sangmeebee.searchmovie.model.mapper.toMovieId
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import com.sangmeebee.searchmovie.util.MutableEventFlow
import com.sangmeebee.searchmovie.util.asEventFlow
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

    private val _errorEvent = MutableEventFlow<Throwable>()
    val errorEvent = _errorEvent.asEventFlow()

    private val bookmarkedMovies: MutableSet<String> = mutableSetOf()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<MovieModel>> =
        query.flatMapLatest {
            fetchMovie(it).map { pagingData: PagingData<Movie> ->
                pagingData.map { movieInfo ->
                    val isBookmarked = bookmarkedMovies.contains(movieInfo.link)
                    movieInfo.toPresentation(
                        isBookmarked = isBookmarked,
                        bookmark = { bookmarkMovie(movieInfo.toMovieBookmark()) }
                    )
                }
            }
        }.cachedIn(viewModelScope)

    fun handleQuery(newQuery: String) = viewModelScope.launch {
        query.emit(newQuery)
    }

    private fun fetchMovie(query: String): Flow<PagingData<Movie>> {
        return getMovieUseCase(query)
    }

    private fun bookmarkMovie(movie: MovieBookmark) = viewModelScope.launch {
        if (bookmarkedMovies.contains(movie.link)) {
            unbookmarkMovieUseCase(movie)
                .onSuccess { bookmarkedMovies.remove(movie.link) }
                .onFailure { _errorEvent.emit(UnBookmarkException()) }
        } else {
            bookmarkMovieUseCase(movie)
                .onSuccess { bookmarkedMovies.add(movie.link) }
                .onFailure { _errorEvent.emit(BookmarkException()) }
        }
    }

    init {
        viewModelScope.launch {
            getAllBookmarkedMovieUseCase()
                .onSuccess { bookmarkedMovies.addAll(it.toMovieId()) }
                .onFailure { _errorEvent.emit(BookmarkException()) }
        }
    }
}