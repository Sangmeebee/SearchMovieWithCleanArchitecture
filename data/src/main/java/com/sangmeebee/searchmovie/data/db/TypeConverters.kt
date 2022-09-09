package com.sangmeebee.searchmovie.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sangmeebee.searchmovie.data.model.MovieInfoResponse


@ProvidedTypeConverter
class MovieTypeConverter {

    @TypeConverter
    fun fromString(value: String): List<MovieInfoResponse> {
        val listType = object : TypeToken<List<MovieInfoResponse>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMovies(type: List<MovieInfoResponse>): String {
        return Gson().toJson(type)
    }
}