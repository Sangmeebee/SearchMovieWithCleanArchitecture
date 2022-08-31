package com.sangmeebee.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.domain.usecase.GetMovieUseCase
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.UIState
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import com.sangmeebee.searchmovie.ui.customException.EmptyQueryException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<MovieModel>>(UIState.Empty)
    val uiState = _uiState.asStateFlow()

    fun getMovie(query: String) = viewModelScope.launch {
        if (query.isEmpty()) {
            _uiState.value = UIState.Error(EmptyQueryException())
            return@launch
        }
        _uiState.value = UIState.Loading
        _uiState.value = getMovieUseCase(query).fold(
            onSuccess = { (UIState.Success(it.toPresentation())) },
            onFailure = { UIState.Error(it) }
        )
    }
}