package com.example.newsly.domain.usecases

import androidx.paging.PagingData
import com.example.newsly.domain.model.News
import com.example.newsly.domain.repository.NewslyRepository
import kotlinx.coroutines.flow.Flow


data class NewslyUseCases(
    val getNews: GetNews,
    val bookmarkNews: BookmarkNews,
    val deleteNews: DeleteNews,
    val getBookmarkedNews: GetBookmarkedNews,
    val isNewsBookmarked: IsNewsBookmarked
)

class BookmarkNews(
    private val newslyRepository: NewslyRepository
) {
    suspend operator fun invoke(news: News){
        newslyRepository.bookmarkNews(news)
    }
}

class IsNewsBookmarked(
    private val newslyRepository: NewslyRepository
) {

    suspend operator fun invoke(url: String): News?{
        return newslyRepository.isNewsBookmarked(url)
    }

}

class GetBookmarkedNews(
    private val newslyRepository: NewslyRepository
) {
    operator fun invoke(): Flow<List<News>>{
        return newslyRepository.getBookmarkedNews()
    }
}

class GetNews(
    private val newslyRepository: NewslyRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<News>>{
        return newslyRepository.getNews(sources = sources)
    }
}

class DeleteNews(
    private val newslyRepository: NewslyRepository
) {
    suspend operator fun invoke(news: News){
        newslyRepository.deleteNews(news)
    }
}
