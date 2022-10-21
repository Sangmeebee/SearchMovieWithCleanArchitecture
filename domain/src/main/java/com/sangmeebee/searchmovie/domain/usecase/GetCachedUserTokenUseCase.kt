package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.UserRepository
import javax.inject.Inject


class GetCachedUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): String? = userRepository.getCacheUserToken()
}