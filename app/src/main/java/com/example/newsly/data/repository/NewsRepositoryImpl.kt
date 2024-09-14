package com.example.newsly.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsly.data.local.NewslyDao
import com.example.newsly.data.remote.NewslyApi
import com.example.newsly.data.remote.NewslyPagingSource
import com.example.newsly.domain.model.News
import com.example.newsly.domain.repository.NewslyRepository
import kotlinx.coroutines.flow.Flow


class NewslyRepositoryImpl(
    private val newslyApi: NewslyApi,
    private val newslyDao: NewslyDao
) : NewslyRepository {

    override fun getNews(sources: List<String>): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewslyPagingSource(
                    newslyApi = newslyApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun bookmarkNews(news: News) {
        newslyDao.bookmarkNews(news)
    }

    override suspend fun deleteNews(news: News) {
      newslyDao.deleteNews(news)
    }

    override fun getBookmarkedNews(): Flow<List<News>> {
      return newslyDao.getBookmarkedNews()
    }

    override suspend fun isNewsBookmarked(url: String): News? {
      return newslyDao.isNewsBookmarked(url)
    }
}