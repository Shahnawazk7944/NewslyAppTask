package com.example.newsly.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.domain.model.News
import com.example.newsly.domain.usecases.NewslyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val newsUseCases: NewslyUseCases
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    private var _isBookmarkedState =
        MutableStateFlow(NewsDetailsStates())
    val isBookmarkedState = _isBookmarkedState.asStateFlow()

    fun onEvent(event: NewsDetailsEvent) {
        when (event) {
            is NewsDetailsEvent.BookmarkOrDeleteNews -> {
                viewModelScope.launch {
                    val isNewsBookmarked = newsUseCases.isNewsBookmarked(event.news.url)
                    if (isNewsBookmarked == null) {
                        bookmarkNews(event.news)
                        _isBookmarkedState.update {
                            it.copy(
                                isBookmarked = true
                            )
                        }
                    } else {
                        deleteNews(event.news)
                        _isBookmarkedState.update {
                            it.copy(
                                isBookmarked = false
                            )
                        }

                    }
                }
            }

            is NewsDetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }

            is NewsDetailsEvent.IsBookmarked -> {
                viewModelScope.launch {
                    val isNewsBookmarked = newsUseCases.isNewsBookmarked(event.news.url)
                    _isBookmarkedState.update {
                        it.copy(
                            isBookmarked = isNewsBookmarked != null
                        )
                    }
                }
            }
        }
    }

    private suspend fun deleteNews(news: News) {
        newsUseCases.deleteNews(news = news)
        sideEffect = "Removed From Bookmarks"
    }

    private suspend fun bookmarkNews(news: News) {
        newsUseCases.bookmarkNews(news = news)
        sideEffect = "Added To Bookmarks"
    }

}
data class NewsDetailsStates(
    val isBookmarked: Boolean = false,
)