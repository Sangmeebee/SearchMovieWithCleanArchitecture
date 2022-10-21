package com.sangmeebee.searchmovie.domain.repository

import com.sangmeebee.searchmovie.domain.model.User

interface UserRepository {
    suspend fun getUserToken(): Result<String>
    suspend fun insertUserToken(token: String): Result<Unit>
    suspend fun deleteUserToken(): Result<Unit>

    fun getCacheUserToken(): String?
    suspend fun getUser(token: String): Result<User>
    suspend fun insertUser(user: User): Result<Unit>
    suspend fun deleteUser(token: String): Result<Unit>
}