package com.sangmeebee.searchmovie.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sangmeebee.searchmovie.cache.model.mapper.CacheToDataMapper
import com.sangmeebee.searchmovie.data.model.UserEntity
import com.sangmeebee.searchmovie.data.model.UserEntityLoginType

@Entity(tableName = "user")
internal data class UserPref(
    @PrimaryKey
    @ColumnInfo(name = "user_token")
    val userToken: String,
    val nickname: String? = null,
    @ColumnInfo(name = "profile_image_url")
    val profileImageUrl: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val age: String? = null,
    @ColumnInfo(name = "login_type")
    val loginType: UserPrefLoginType,
) : CacheToDataMapper<UserEntity> {
    override fun toData(): UserEntity = UserEntity(
        userToken = userToken,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        email = email,
        gender = gender,
        age = age,
        loginType = when (loginType) {
            UserPrefLoginType.KAKAO -> UserEntityLoginType.KAKAO
            UserPrefLoginType.GOOGLE -> UserEntityLoginType.GOOGLE
            UserPrefLoginType.NAVER -> UserEntityLoginType.NAVER
        },
    )
}

enum class UserPrefLoginType {
    KAKAO, GOOGLE, NAVER
}
