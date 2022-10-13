package com.sangmeebee.searchmovie.util.social_login

import android.content.Context
import com.sangmeebee.searchmovie.model.UserModel

interface SocialLoginUtil {
    suspend fun login(context: Context): Result<String>
    suspend fun logout(context: Context): Result<Boolean>
    suspend fun isLogin(): Boolean
    suspend fun getUserInfo(): Result<UserModel>
}