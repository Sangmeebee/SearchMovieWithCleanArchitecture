package com.sangmeebee.searchmovie.data

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.searchmovie.data.model.MovieInfoResponse
import com.sangmeebee.searchmovie.data.model.MovieResponse
import com.sangmeebee.searchmovie.data.service.MovieAPI
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MovieAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieAPI: MovieAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        movieAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    @Test
    fun `해당 쿼리의 데이터가 없으면 비어있는 아이템을_반환한다`() = runTest {
        // given
        val response =
            MockResponse()
                .setResponseCode(200)
                .setBody(File("src/test/resources/repositories/empty.json").readText())
        mockWebServer.enqueue(response)

        // when
        val actual = movieAPI.getMovies("")
        advanceUntilIdle()

        // then
        val expected = MovieResponse(
            offsetTime = "",
            totalCount = 0,
            pageStart = 1,
            pageSize = 0,
            items = emptyList())
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun 해당_쿼리의_데이터가_존재하면_영화_리스트를_반환한다() = runTest {
        //given
        val response =
            MockResponse()
                .setResponseCode(200)
                .setBody(File("src/test/resources/repositories/200.json").readText())
        mockWebServer.enqueue(response)

        //when
        val actual = movieAPI.getMovies("")
        advanceUntilIdle()

        //then
        val movieInfo = MovieInfoResponse(
            title = "<b>광해</b>, 왕이 된 남자",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=83893",
            imageUrl = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0838/83893_P09_112146.jpg",
            releaseDate = "2012",
            director = "추창민|",
            actor = "이병헌|류승룡|한효주|",
            userRating = "9.25"
        )
        val expected = MovieResponse(
            offsetTime = "",
            totalCount = 1,
            pageStart = 1,
            pageSize = 1,
            items = listOf(movieInfo))
        assertThat(actual).isEqualTo(expected)
    }
}