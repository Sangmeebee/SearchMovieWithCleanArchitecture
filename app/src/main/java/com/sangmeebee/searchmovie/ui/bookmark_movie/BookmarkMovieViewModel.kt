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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkMovieViewModel @Inject constructor(
    private val getCachedUserTokenUseCase: GetCachedUserTokenUseCase,
    getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
) : ViewModel() {

    // TODO UserTokenFlow를 transform 해야한다.
    val bookmarkedMovieFlow: StateFlow<List<MovieModel>> = getBookmarkedMoviesUseCase("userToken").map { it.toPresentation() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

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
            _uiState.update { it.copy(IsNeedToLogin = true) }
        }
    }

    fun fetchIsNeedToLogin() {
        _uiState.update { it.copy(IsNeedToLogin = false) }
    }

    fun fetchError(throwable: Throwable?) {
        _uiState.update { it.copy(error = throwable) }
    }

    fun fetchBookmarkedMovies(bookmarkedMovies: List<MovieModel>) {
        _uiState.update { it.copy(bookmarkedMovies = bookmarkedMovies) }
    }
}