package com.sangmeebee.searchmovie.ui.bookmark_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.cache.util.BookmarkException
import com.sangmeebee.searchmovie.cache.util.UnBookmarkException
import com.sangmeebee.searchmovie.domain.usecase.BookmarkMovieUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetBookmarkedMoviesUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetCachedUserTokenUseCase
import com.sangmeebee.searchmovie.domain.usecase.UnbookmarkMovieUseCase
import com.sangmeebee.searchmovie.model.BookmarkMovieUiState
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.mapper.toDomain
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkMovieViewModel @Inject constructor(
    private val getCachedUserTokenUseCase: GetCachedUserTokenUseCase,
    private val getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookmarkMovieUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchBookmark(movieModel: MovieModel, isBookmarked: Boolean) = viewModelScope.launch {
        val userToken = getCachedUserTokenUseCase()
        if (userToken != null) {
            if (!isBookmarked) {
                bookmarkMovieUseCase(userToken, movieModel.toDomain())
                    .onFailure { _uiState.update { it.copy(error = BookmarkException()) } }
            } else {
                unbookmarkMovieUseCase(userToken, movieModel.link)
                    .onFailure { _uiState.update { it.copy(error = UnBookmarkException()) } }
            }
        } else {
            // TODO GO TO Login Screen
        }
    }

    fun fetchError(throwable: Throwable?) {
        _uiState.update { it.copy(error = throwable) }
    }

    init {
        viewModelScope.launch {
            getBookmarkedMoviesUseCase().collectLatest { bookmarkedMovies ->
                _uiState.update { uiState ->
                    uiState.copy(bookmarkedMovies = bookmarkedMovies.toPresentation())
                }
            }
        }
    }
}