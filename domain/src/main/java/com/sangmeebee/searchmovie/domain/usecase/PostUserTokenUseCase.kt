package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.UserRepository
import javax.inject.Inject

class PostUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val fetchInitBookmarkedMoviesUseCase: FetchInitBookmarkedMoviesUseCase,
) {
    suspend operator fun invoke(token: String): Result<Unit> {
        fetchInitBookmarkedMoviesUseCase(token)
        return userRepository.insertUserToken(token)
    }

}