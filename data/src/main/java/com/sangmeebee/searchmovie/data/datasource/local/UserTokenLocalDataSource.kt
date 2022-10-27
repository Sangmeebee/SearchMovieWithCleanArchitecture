package com.sangmeebee.searchmovie.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface UserTokenLocalDataSource {
    fun getCacheUserToken(): String?
    val userTokenFlow: Flow<String?>
    suspend fun initUserToken(): Result<String>
    suspend fun insertUserToken(token: String): Result<Unit>
    suspend fun deleteUserToken(): Result<Unit>
}