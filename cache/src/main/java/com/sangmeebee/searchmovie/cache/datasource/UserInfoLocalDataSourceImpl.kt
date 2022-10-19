package com.sangmeebee.searchmovie.cache.datasource

import com.sangmeebee.searchmovie.cache.db.UserInfoDao
import com.sangmeebee.searchmovie.cache.model.mapper.toPref
import com.sangmeebee.searchmovie.data.datasource.local.UserInfoLocalDataSource
import com.sangmeebee.searchmovie.data.model.UserEntity
import javax.inject.Inject

internal class UserInfoLocalDataSourceImpl @Inject constructor(
    private val userInfoDao: UserInfoDao,
) : UserInfoLocalDataSource {
    override suspend fun getUser(token: String): Result<UserEntity> = runCatching {
        userInfoDao.getUser(token).toData()
    }

    override suspend fun insertUser(user: UserEntity): Result<Unit> = runCatching {
        userInfoDao.insert(user.toPref())
    }

    override suspend fun deleteUser(token: String): Result<Unit> = runCatching {
        userInfoDao.deleteMovieBookmark(token)
    }
}