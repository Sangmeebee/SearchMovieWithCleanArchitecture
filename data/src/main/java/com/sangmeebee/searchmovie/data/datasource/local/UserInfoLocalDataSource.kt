package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.data.model.UserEntity

interface UserInfoLocalDataSource {
    suspend fun getUser(token: String): Result<UserEntity>
    suspend fun insertUser(user: UserEntity): Result<Unit>
    suspend fun deleteUser(token: String): Result<Unit>
}