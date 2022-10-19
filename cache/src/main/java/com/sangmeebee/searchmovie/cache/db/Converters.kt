package com.sangmeebee.searchmovie.cache.db

import androidx.room.TypeConverter
import com.sangmeebee.searchmovie.cache.model.UserPrefLoginType

class Converters {
    @TypeConverter
    fun fromLoginType(value: String): UserPrefLoginType {
        return when (value) {
            "KAKAO" -> UserPrefLoginType.KAKAO
            "GOOGLE" -> UserPrefLoginType.GOOGLE
            else -> UserPrefLoginType.NAVER
        }
    }

    @TypeConverter
    fun stringToLoginType(loginType: UserPrefLoginType): String {
        return loginType.name
    }
}