package com.sangmeebee.searchmovie.data.datasource.local

interface UserTokenLocalDataSource {
    fun getCacheUserToken(): String?
    suspend fun getUserToken(): Result<String>
    suspend fun insertUserToken(token: String): Result<Unit>
    suspend fun deleteUserToken(): Result<Unit>
}