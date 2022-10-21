package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val fetchInitBookmarkedMoviesUseCase: FetchInitBookmarkedMoviesUseCase,
) {
    suspend operator fun invoke(): Result<Unit> {
        fetchInitBookmarkedMoviesUseCase(null)
        return userRepository.deleteUserToken()
    }

}