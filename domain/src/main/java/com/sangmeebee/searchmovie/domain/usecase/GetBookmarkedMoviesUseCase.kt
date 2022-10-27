package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(userToken: String): Flow<List<BookmarkedMovie>> = movieRepository.getBookmarkedMovies(userToken)
}