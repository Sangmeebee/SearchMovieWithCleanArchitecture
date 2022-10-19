package com.sangmeebee.searchmovie.cache.datasource

import androidx.datastore.core.DataStore
import com.sangmeebee.searchmovie.cache.UserTokenPref
import com.sangmeebee.searchmovie.data.datasource.local.UserTokenLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserTokenLocalDataSourceImpl @Inject constructor(
    private val userTokenDataStore: DataStore<UserTokenPref>,
) : UserTokenLocalDataSource {

    override val userTokenFlow: Flow<String>
        get() = userTokenDataStore.data.map { it.token }

    override suspend fun getUserToken(): Result<String> = runCatching {
        userTokenDataStore.data.first().token
    }

    override suspend fun insertUserToken(token: String): Result<Unit> = runCatching {
        userTokenDataStore.updateData { userToken ->
            userToken.toBuilder()
                .setToken(token)
                .build()
        }
    }

    override suspend fun deleteUserToken(): Result<Unit> = runCatching {
        userTokenDataStore.updateData { userToken ->
            userToken.toBuilder()
            userToken.toBuilder()
                .setToken("")
                .build()
        }
    }
}