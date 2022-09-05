package com.sangmeebee.searchmovie.domain.usecase

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.domain.model.MovieInfo
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(query: String): Flow<PagingData<MovieInfo>> =
        movieRepository.getMovies(query)

}