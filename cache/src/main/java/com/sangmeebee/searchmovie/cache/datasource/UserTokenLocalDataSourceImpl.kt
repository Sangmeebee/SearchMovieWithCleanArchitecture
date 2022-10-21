package com.sangmeebee.searchmovie.cache.datasource

import androidx.datastore.core.DataStore
import com.sangmeebee.searchmovie.cache.UserTokenPref
import com.sangmeebee.searchmovie.data.datasource.local.UserTokenLocalDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserTokenLocalDataSourceImpl @Inject constructor(
    private val userTokenDataStore: DataStore<UserTokenPref>,
) : UserTokenLocalDataSource {

    private var cachedUserInfo: String? = null

    override fun getCacheUserToken(): String? = cachedUserInfo

    override suspend fun getUserToken(): Result<String> = runCatching {
        userTokenDataStore.data.first().token.apply {
            cachedUserInfo = this.ifEmpty { null }
        }
    }

    override suspend fun insertUserToken(token: String): Result<Unit> = runCatching {
        userTokenDataStore.updateData { userToken ->
            userToken.toBuilder()
                .setToken(token)
                .build()
        }
        cachedUserInfo = token
    }

    override suspend fun deleteUserToken(): Result<Unit> = runCatching {
        userTokenDataStore.updateData { userToken ->
            userToken.toBuilder()
            userToken.toBuilder()
                .setToken("")
                .build()
        }
        cachedUserInfo = null
    }
}