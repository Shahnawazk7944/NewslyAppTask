package com.example.newsly.data.remote

import android.util.Log
import androidx.paging.LOG_TAG
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsly.domain.model.News
import kotlinx.coroutines.delay


class NewslyPagingSource(
    private val newslyApi: NewslyApi,
    private val sources: String
) : PagingSource<Int, News>() {

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val page = params.key ?: 1

        return try {
            val newsResponse = newslyApi.getNews(sources = sources, page = page)
            delay(2000)
            totalNewsCount += newsResponse.articles.size
            val listOfNews = newsResponse.articles.distinctBy { it.title } // Remove Duplicates
            LoadResult.Page(
                data = listOfNews,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}