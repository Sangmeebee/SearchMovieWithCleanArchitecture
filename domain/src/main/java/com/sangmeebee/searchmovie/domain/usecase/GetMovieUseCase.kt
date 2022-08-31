package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import com.sangmeebee.searchmovie.domain.util.EmptyQueryException
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    private var currentQuery = ""

    private suspend fun getMovieWithCurrentQuery() =
        if (currentQuery.isEmpty()) {
            Result.failure(EmptyQueryException())
        } else {
            movieRepository.getMovies(currentQuery)
        }

    suspend operator fun invoke(query: String?): Result<Movie> =
        if (query != null) {
            currentQuery = query
            movieRepository.getMovies(query)
        } else {
            getMovieWithCurrentQuery()
        }
}