package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<String> = userRepository.userTokenFlow
}