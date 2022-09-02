package com.sangmeebee.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.domain.usecase.GetMovieUseCase
import com.sangmeebee.searchmovie.domain.util.EmptyQueryException
import com.sangmeebee.searchmovie.model.MovieUIState
import com.sangmeebee.searchmovie.model.UIState
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<MovieUIState>>>(UIState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow<String?>("")
    val query = _query.asStateFlow()

    fun fetchMovie(newQuery: String?, display: Int = 20, start: Int = 1) =
        viewModelScope.launch {
            _uiState.value = UIState.Loading

            val currentQuery = newQuery ?: query.value!!
            if (currentQuery.isEmpty()) {
                _uiState.value = UIState.Error(EmptyQueryException())
                return@launch
            }

            _uiState.value = getMovieUseCase(currentQuery, display, start).fold(
                onSuccess = {
                    fetchQuery(currentQuery)
                    val movies = it.items.toPresentation()
                    if (movies.isEmpty()) {
                        return@fold UIState.Empty
                    }
                    UIState.Success(movies)
                },
                onFailure = { UIState.Error(it) }
            )
        }

    private fun fetchQuery(query: String) {
        _query.value = null
        _query.value = query
    }
}