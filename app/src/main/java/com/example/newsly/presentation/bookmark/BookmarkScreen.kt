package com.example.newsly.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.newsly.domain.model.News
import com.example.newsly.presentation.common.NewsListForBookmark
import com.example.newsly.ui.theme.spacing


@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (News) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MaterialTheme.spacing.extraLarge, start = MaterialTheme.spacing.extraLarge, end = MaterialTheme.spacing.extraLarge)
    ) {
        Text(
            text = "Bookmarks",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))

        NewsListForBookmark(articles = state.listOfBookmarkedNews, onClick = { navigateToDetails(it) })
    }
}