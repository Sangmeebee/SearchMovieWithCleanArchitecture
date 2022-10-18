package com.sangmeebee.searchmovie.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sangmeebee.searchmovie.cache.model.BookmarkedMoviePref
import com.sangmeebee.searchmovie.cache.model.UserPref

@Database(
    entities = [BookmarkedMoviePref::class, UserPref::class],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
internal abstract class AppDataBase : RoomDatabase() {
    abstract fun movieBookmarkDao(): MovieBookmarkDao
}