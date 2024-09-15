package com.example.newsly.presentation.details

import com.example.newsly.domain.model.News


sealed class NewsDetailsEvent {
    data class BookmarkOrDeleteNews(val news: News) : NewsDetailsEvent()
    data class IsBookmarked(val news: News) : NewsDetailsEvent()
    object RemoveSideEffect : NewsDetailsEvent()
}