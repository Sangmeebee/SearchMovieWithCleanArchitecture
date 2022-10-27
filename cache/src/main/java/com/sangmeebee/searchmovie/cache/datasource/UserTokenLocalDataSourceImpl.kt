package com.sangmeebee.searchmovie.cache.datasource

import androidx.datastore.core.DataStore
import com.sangmeebee.searchmovie.cache.UserTokenPref
import com.sangmeebee.searchmovie.data.datasource.local.UserTokenLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserTokenLocalDataSourceImpl @Inject constructor(
    private val userTokenDataStore: DataStore<UserTokenPref>,
) : UserTokenLocalDataSource {

    private var cachedUserInfo: String? = null

    override fun getCacheUserToken(): String? = cachedUserInfo

    override val userTokenFlow: Flow<String?> = userTokenDataStore.data
        .map {
            it.token.ifEmpty { null }
        }
        .catch { exception ->
            if (exception is IOException) {
                emit(null)
            } else {
                throw exception
            }
        }

    override suspend fun initUserToken(): Result<String> = runCatching {
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