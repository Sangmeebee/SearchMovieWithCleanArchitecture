package com.sangmeebee.searchmovie.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sangmeebee.searchmovie.data.model.mapper.toDomain
import com.sangmeebee.searchmovie.data.service.MovieAPI
import com.sangmeebee.searchmovie.domain.model.MovieInfo
import com.sangmeebee.searchmovie.domain.util.EmptyQueryException
import retrofit2.HttpException
import java.io.IOException

internal class MoviePagingDataSource(
    private val movieAPI: MovieAPI,
    private val query: String,
) : PagingSource<Int, MovieInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieInfo> {
        try {
            if (query.isEmpty()) {
                throw EmptyQueryException()
            }
            val start = params.key ?: STARTING_PAGE_INDEX
            val response = movieAPI.getMovies(
                query = query,
                display = PAGE_DISPLAY_SIZE,
                start = start
            )
            return LoadResult.Page(
                data = response.items.toDomain(),
                prevKey = if (start == STARTING_PAGE_INDEX) null else start - PAGE_DISPLAY_SIZE, // Only paging forward.
                nextKey = if (response.totalCount < (start + PAGE_DISPLAY_SIZE).toLong()) null else start + PAGE_DISPLAY_SIZE
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: EmptyQueryException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(PAGE_DISPLAY_SIZE)
                ?: anchorPage?.nextKey?.minus(PAGE_DISPLAY_SIZE)
        }

    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_DISPLAY_SIZE = 20
    }
}