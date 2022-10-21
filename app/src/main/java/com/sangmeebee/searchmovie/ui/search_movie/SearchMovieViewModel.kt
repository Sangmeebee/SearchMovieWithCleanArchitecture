package com.sangmeebee.searchmovie.ui.search_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sangmeebee.searchmovie.cache.util.BookmarkException
import com.sangmeebee.searchmovie.cache.util.UnBookmarkException
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.usecase.*
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.SearchMovieUiState
import com.sangmeebee.searchmovie.model.mapper.toDomain
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import com.sangmeebee.searchmovie.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
    getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
) : ViewModel() {

    private val query = MutableEventFlow<String>()

    private val bookmarkedMovies: Flow<List<MovieModel>> = getBookmarkedMoviesUseCase().map { it.toPresentation() }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchMovies: Flow<PagingData<MovieModel>> =
        query.flatMapLatest {
            fetchSearchMovie(it).map { pagingData: PagingData<Movie> ->
                pagingData.map { movieInfo ->
                    movieInfo.toPresentation(isBookmarked = false)
                }
            }
        }.cachedIn(viewModelScope)

    private val movies: Flow<PagingData<MovieModel>> = searchMovies.combine(bookmarkedMovies) { pagingSearchMovie, bookmarkedMovies ->
        pagingSearchMovie.map { searchMovie: MovieModel ->
            val isBookmarked = bookmarkedMovies.any { bookmarkedMovies ->
                bookmarkedMovies.link == searchMovie.link
            }
            searchMovie.copy(isBookmarked = isBookmarked)
        }
    }

    private val _uiState = MutableStateFlow(SearchMovieUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchQuery(newQuery: String) = viewModelScope.launch {
        query.emit(newQuery)
    }

    private fun fetchSearchMovie(query: String): Flow<PagingData<Movie>> = getMovieUseCase(query)


    fun fetchBookmark(movieModel: MovieModel, isBookmarked: Boolean) = viewModelScope.launch {
        getUserTokenUseCase().collectLatest { userToken ->
            if (userToken.isNotEmpty()) {
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
    }


    init {
        viewModelScope.launch {
            movies.collectLatest { moviePagingData ->
                _uiState.update { it.copy(movies = moviePagingData) }
            }
        }
    }
}