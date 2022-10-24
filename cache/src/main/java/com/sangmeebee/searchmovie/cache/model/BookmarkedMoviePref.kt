package com.sangmeebee.searchmovie.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.sangmeebee.searchmovie.cache.model.mapper.CacheToDataMapper
import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity

@Entity(
    tableName = "movie_bookmark",
    primaryKeys = ["movie_id", "user_owner_token"],
    foreignKeys = [
        ForeignKey(
            entity = UserPref::class,
            parentColumns = ["user_token"],
            childColumns = ["user_owner_token"],
            onDelete = CASCADE
        )
    ]
)
internal data class BookmarkedMoviePref(
    val title: String,
    val subtitle: String? = null,
    @ColumnInfo(name = "movie_id")
    val link: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    val contributor: String,
    @ColumnInfo(name = "user_rating")
    val userRating: Float,
    @ColumnInfo(name = "user_owner_token")
    val userOwnerToken: String = "",
) : CacheToDataMapper<BookmarkedMovieEntity> {
    override fun toData(): BookmarkedMovieEntity = BookmarkedMovieEntity(
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        contributor = contributor,
        userRating = userRating
    )
}