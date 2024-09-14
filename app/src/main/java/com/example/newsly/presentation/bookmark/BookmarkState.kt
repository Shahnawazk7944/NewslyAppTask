package com.example.newsly.presentation.bookmark

import com.example.newsly.domain.model.News


data class BookmarkState(
    val listOfBookmarkedNews: List<News> = emptyList()
)
