package com.example.newsly.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.domain.model.News
import com.example.newsly.domain.usecases.NewslyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val newsUseCases: NewslyUseCases
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    var isBookmarked by mutableStateOf<Boolean>(false)
        private set

    fun onEvent(event: NewsDetailsEvent) {
        when (event) {
            is NewsDetailsEvent.BookmarkOrDeleteNews -> {
                viewModelScope.launch {
                    val isNewsBookmarked = newsUseCases.isNewsBookmarked(event.news.url)
                    if (isNewsBookmarked == null) {
                        bookmarkNews(event.news)
                    } else {
                        deleteNews(event.news)
                    }
                }
            }

            is NewsDetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }

            is NewsDetailsEvent.IsBookmarked -> {
                viewModelScope.launch {
                    val isNewsBookmarked = newsUseCases.isNewsBookmarked(event.news.url)
                    isBookmarked = isNewsBookmarked != null
                }
            }
        }
    }

    private suspend fun deleteNews(news: News) {
        newsUseCases.deleteNews(news = news)
        sideEffect = "News Deleted"
    }

    private suspend fun bookmarkNews(news: News) {
        newsUseCases.bookmarkNews(news = news)
        sideEffect = "News Saved"
    }

}