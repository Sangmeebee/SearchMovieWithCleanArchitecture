package com.sangmeebee.searchmovie.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sangmeebee.searchmovie.data.model.MovieEntity
import com.sangmeebee.searchmovie.remote.model.mapper.toData
import com.sangmeebee.searchmovie.remote.service.MovieAPI
import com.sangmeebee.searchmovie.remote.util.EmptyQueryException
import com.sangmeebee.searchmovie.remote.util.HttpConnectionException
import retrofit2.HttpException
import java.io.IOException

internal class MoviePagingSource(
    private val movieAPI: MovieAPI,
    private val query: String,
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
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
                data = response.items.toData(),
                prevKey = if (start == STARTING_PAGE_INDEX) null else start - PAGE_DISPLAY_SIZE, // Only paging forward.
                nextKey = if (response.totalCount < (start + PAGE_DISPLAY_SIZE).toLong()) null else start + PAGE_DISPLAY_SIZE
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(HttpConnectionException())
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: EmptyQueryException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
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