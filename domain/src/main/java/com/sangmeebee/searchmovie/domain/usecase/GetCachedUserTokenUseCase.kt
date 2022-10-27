package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCachedUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    val userTokenFlow: Flow<String?> get() = userRepository.userTokenFlow

    operator fun invoke(): String? = userRepository.getCacheUserToken()
}