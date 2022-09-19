package com.sangmeebee.searchmovie.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.domain.model.MovieBookmark

@Entity(tableName = "movie_bookmark")
data class MovieBookmarkEntity(
    val title: String,
    val subtitle: String? = null,
    @PrimaryKey
    val link: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    val contributor: String,
    @ColumnInfo(name = "user_rating")
    val userRating: Float,
) : DataToDomainMapper<MovieBookmark> {
    override fun toDomain(): MovieBookmark = MovieBookmark(
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        contributor = contributor,
        userRating = userRating
    )
}