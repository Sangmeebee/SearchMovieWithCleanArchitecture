package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.UserRepository
import javax.inject.Inject


class GetInitUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val fetchInitBookmarkedMoviesUseCase: FetchInitBookmarkedMoviesUseCase,
) {
    suspend operator fun invoke() =
        userRepository.getUserToken()
            .onSuccess { userToken ->
                if (userToken.isNotEmpty()) {
                    fetchInitBookmarkedMoviesUseCase(userToken)
                } else {
                    fetchInitBookmarkedMoviesUseCase(null)
                }
            }
            .onFailure { fetchInitBookmarkedMoviesUseCase(null) }
}