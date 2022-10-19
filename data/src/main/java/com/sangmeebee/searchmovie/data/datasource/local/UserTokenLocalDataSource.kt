package com.sangmeebee.searchmovie.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface UserTokenLocalDataSource {
    val userTokenFlow: Flow<String>
    suspend fun getUserToken(): Result<String>
    suspend fun insertUserToken(token: String): Result<Unit>
    suspend fun deleteUserToken(): Result<Unit>
}