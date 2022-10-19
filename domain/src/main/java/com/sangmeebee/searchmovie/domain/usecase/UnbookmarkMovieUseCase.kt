package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import javax.inject.Inject

class UnbookmarkMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(userToken: String, movieId: String) =
        movieRepository.unbookmark(userToken, movieId)
}