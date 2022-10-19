package com.sangmeebee.searchmovie.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sangmeebee.searchmovie.cache.model.UserPref

@Dao
internal interface UserInfoDao {

    @Query("SELECT * FROM user WHERE user_token = :userToken")
    suspend fun getUser(userToken: String): UserPref

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserPref)

    @Query("DELETE FROM user WHERE user_token = :userToken")
    suspend fun deleteMovieBookmark(userToken: String)
}