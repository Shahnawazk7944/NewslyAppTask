package com.example.newsly.domain.repository

import androidx.paging.PagingData
import com.example.newsly.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewslyRepository {

    fun getNews(sources: List<String>): Flow<PagingData<News>>

    suspend fun bookmarkNews(news: News)

    suspend fun deleteNews(news: News)

    fun getBookmarkedNews(): Flow<List<News>>

    suspend fun isNewsBookmarked(url: String): News?

}