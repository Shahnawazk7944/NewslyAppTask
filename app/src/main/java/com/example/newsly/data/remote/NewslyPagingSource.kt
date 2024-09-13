package com.example.newsly.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsly.domain.model.News


class NewslyPagingSource(
    private val newslyApi: NewslyApi,
    private val sources: String
) : PagingSource<Int, News>() {

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val page = params.key ?: 1

        return try {
            val newsResponse = newslyApi.getNews(sources = sources, page = page)
            totalNewsCount += newsResponse.listOfNews.size
            val listOfNews = newsResponse.listOfNews.distinctBy { it.title } // Remove Duplicates
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