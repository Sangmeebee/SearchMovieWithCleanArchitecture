package com.sangmeebee.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sangmeebee.searchmovie.domain.model.MovieInfo
import com.sangmeebee.searchmovie.domain.usecase.GetMovieUseCase
import com.sangmeebee.searchmovie.model.MovieUIState
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {

    private val _query = MutableSharedFlow<String>()
    val query = _query.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<MovieUIState>> = query.flatMapLatest {
        fetchMovie(it).map { pagingData: PagingData<MovieInfo> ->
            pagingData.map { movieInfo ->
                movieInfo.toPresentation()
            }
        }
    }.cachedIn(viewModelScope)

    fun handleQuery(newQuery: String) = viewModelScope.launch {
        _query.emit(newQuery)
    }

    fun fetchMovie(query: String): Flow<PagingData<MovieInfo>> {
        return getMovieUseCase(query)
    }
}