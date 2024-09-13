package com.example.newsly.data.remote.dto

import com.example.newsly.domain.model.News


data class NewslyResponse(
    val listOfNews: List<News>,
    val status: String,
    val totalResults: Int
)