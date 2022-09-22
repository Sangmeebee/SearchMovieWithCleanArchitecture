package com.sangmeebee.searchmovie.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sangmeebee.searchmovie.cache.model.MovieBookmarkPref

@Database(entities = [MovieBookmarkPref::class], version = 2, exportSchema = true)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieBookmarkDao(): MovieBookmarkDao
}