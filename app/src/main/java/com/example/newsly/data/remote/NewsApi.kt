package com.example.newsly.data.remote

import com.example.newsly.data.remote.dto.NewslyResponse
import com.example.newsly.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewslyApi {

    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
//        @Query("country") country: String = "india",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewslyResponse
}