package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.UserRepository
import javax.inject.Inject


class InitUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = userRepository.initUserToken()
}