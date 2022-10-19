package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.model.User
import com.sangmeebee.searchmovie.domain.repository.UserRepository
import javax.inject.Inject

class PostUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(user: User) =
        userRepository.insertUser(user)
}