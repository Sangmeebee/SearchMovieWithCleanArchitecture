package com.sangmeebee.searchmovie.ui.search_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sangmeebee.searchmovie.cache.util.BookmarkException
import com.sangmeebee.searchmovie.cache.util.GetBookmarkException
import com.sangmeebee.searchmovie.cache.util.UnBookmarkException
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.usecase.BookmarkMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetAllBookmarkedMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.UnbookmarkMovieUseCase
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.mapper.toDomain
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import com.sangmeebee.searchmovie.util.MutableEventFlow
import com.sangmeebee.searchmovie.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<MovieModel>> =
        query.flatMapLatest {
            fetchMovie(it).map { pagingData: PagingData<Movie> ->
                pagingData.map { movieInfo ->
                    val isBookmarked = bookmarkedMovies.any { bookmarkedMovies ->
                        bookmarkedMovies.link == movieInfo.link
                    }
                    movieInfo.toPresentation(isBookmarked = isBookmarked)
                }
            }
        }.cachedIn(viewModelScope)

    private val _errorEvent = MutableEventFlow<Throwable>()
    val errorEvent = _errorEvent.asEventFlow()

    private val _bookmarkedMovieState = MutableStateFlow<List<MovieModel>>(emptyList())
    val bookmarkedMovieState: StateFlow<List<MovieModel>> = _bookmarkedMovieState.asStateFlow()

    private val _bookmarkEvent = MutableEventFlow<MovieModel>()
    val bookmarkEvent = _bookmarkEvent.asEventFlow()

    private val bookmarkedMovies = mutableSetOf<MovieModel>()

    fun handleQuery(newQuery: String) = viewModelScope.launch {
        query.emit(newQuery)
    }

    private fun fetchMovie(query: String): Flow<PagingData<Movie>> {
        return getMovieUseCase(query)
    }

    fun bookmarkMovie(movie: MovieModel) = viewModelScope.launch {
        var bookmarkMovie: MovieModel? = null
        if (bookmarkedMovies.any { it.link == movie.link }) {
            unbookmarkMovieUseCase(movie.link)
                .onSuccess {
                    bookmarkMovie = movie.copy(isBookmarked = false)
                    bookmarkedMovies.remove(bookmarkedMovies.find { it.link == movie.link })
                }
                .onFailure { _errorEvent.emit(UnBookmarkException()) }
        } else {
            bookmarkMovieUseCase(movie.toDomain())
                .onSuccess {
                    bookmarkMovie = movie.copy(isBookmarked = true)
                    bookmarkedMovies.add(bookmarkMovie!!)
                }
                .onFailure { _errorEvent.emit(BookmarkException()) }
        }
        _bookmarkedMovieState.value = bookmarkedMovies.toList()
        bookmarkMovie?.let { _bookmarkEvent.emit(it) }
    }

    init {
        viewModelScope.launch {
            getAllBookmarkedMovieUseCase()
                .onSuccess { bookmarkedMovies.addAll(it.toPresentation()) }
                .onFailure { _errorEvent.emit(GetBookmarkException()) }
            _bookmarkedMovieState.value = bookmarkedMovies.toList()
        }
    }
}