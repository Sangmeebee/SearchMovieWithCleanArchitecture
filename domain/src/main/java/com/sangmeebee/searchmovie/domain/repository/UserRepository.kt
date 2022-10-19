package com.sangmeebee.searchmovie.domain.repository

import com.sangmeebee.searchmovie.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userTokenFlow: Flow<String>
    suspend fun getUserToken(): Result<String>
    suspend fun insertUserToken(token: String): Result<Unit>
    suspend fun deleteUserToken(): Result<Unit>

    suspend fun getUser(token: String): Result<User>
    suspend fun insertUser(user: User): Result<Unit>
    suspend fun deleteUser(token: String): Result<Unit>
}