package com.sangmeebee.searchmovie.data.repository

import com.sangmeebee.searchmovie.data.datasource.local.UserInfoLocalDataSource
import com.sangmeebee.searchmovie.data.datasource.local.UserTokenLocalDataSource
import com.sangmeebee.searchmovie.data.model.mapper.toData
import com.sangmeebee.searchmovie.domain.model.User
import com.sangmeebee.searchmovie.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userTokenLocalDataSource: UserTokenLocalDataSource,
    private val userInfoLocalDataSource: UserInfoLocalDataSource,
) : UserRepository {

    override fun getCacheUserToken(): String? = userTokenLocalDataSource.getCacheUserToken()

    override suspend fun getUserToken(): Result<String> =
        userTokenLocalDataSource.getUserToken()

    override suspend fun insertUserToken(token: String): Result<Unit> =
        userTokenLocalDataSource.insertUserToken(token)

    override suspend fun deleteUserToken(): Result<Unit> =
        userTokenLocalDataSource.deleteUserToken()

    override suspend fun getUser(token: String): Result<User> =
        userInfoLocalDataSource.getUser(token).map { it.toDomain() }

    override suspend fun insertUser(user: User): Result<Unit> =
        userInfoLocalDataSource.insertUser(user.toData())

    override suspend fun deleteUser(token: String): Result<Unit> =
        userInfoLocalDataSource.deleteUser(token)
}