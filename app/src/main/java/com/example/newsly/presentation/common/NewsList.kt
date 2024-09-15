package com.example.newsly.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsly.domain.model.News
import com.example.newsly.ui.theme.spacing

@Composable
fun NewsListForBookmark(
    modifier: Modifier = Modifier,
    articles: List<News>,
    onClick: (News) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraLarge),
        contentPadding = PaddingValues(all = MaterialTheme.spacing.small)
    ) {
        items(count = articles.size) {
            val article = articles[it]
            NewsCard(news = article, onClick = { onClick(article) })
        }
    }
}

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    listOfNews: LazyPagingItems<News>,
    onClick: (News) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = listOfNews)
    if (handlePagingResult) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Display items in two columns
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            contentPadding = PaddingValues(all = MaterialTheme.spacing.extraSmall)
        ) {
            items(listOfNews.itemCount) {
                listOfNews[it]?.let {
                    NewsCard(news = it, onClick = { onClick(it) })
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<News>,
): Boolean {

    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            NewsShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error)
            false
        }

        else -> {
            true
        }
    }

}

@Composable
private fun NewsShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraLarge)) {
        repeat(10) {
            NewsCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraLarge)
            )
        }
    }
}

@Composable
fun BookmarkShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraLarge),
        modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(
            top = MaterialTheme.spacing.extraLarge,
        )) {
        Text(
            text = "Bookmarks",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.extraLarge,
                )
        )
        repeat(10) {
            BookmarkCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraLarge)
            )
        }
    }
}