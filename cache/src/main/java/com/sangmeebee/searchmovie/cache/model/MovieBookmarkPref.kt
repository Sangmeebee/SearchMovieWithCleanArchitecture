package com.sangmeebee.searchmovie.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sangmeebee.searchmovie.cache.model.mapper.CacheToDataMapper
import com.sangmeebee.searchmovie.data.model.MovieBookmarkEntity

@Entity(tableName = "movie_bookmark")
internal data class MovieBookmarkPref(
    val title: String,
    val subtitle: String? = null,
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val link: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    val contributor: String,
    @ColumnInfo(name = "user_rating")
    val userRating: Float,
    val userOwnerId: String
) : CacheToDataMapper<MovieBookmarkEntity> {
    override fun toData(): MovieBookmarkEntity = MovieBookmarkEntity(
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        contributor = contributor,
        userRating = userRating
    )
}