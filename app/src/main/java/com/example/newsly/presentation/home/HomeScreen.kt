package com.example.newsly.presentation.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.newsly.R
import com.example.newsly.domain.model.News
import com.example.newsly.presentation.common.NewsList
import com.example.newsly.ui.theme.spacing
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    listOfNews: LazyPagingItems<News>,
    navigateToDetails: (News) -> Unit,
    event: (HomeEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.spacing.large)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = if(isSystemInDarkTheme())
                R.drawable.news_logo_with_title_dark else R.drawable.news_logo_with_title_light),
            contentDescription = null,
            modifier = Modifier
                .width(180.dp)
                .height(50.dp)
                .padding(horizontal = MaterialTheme.spacing.extraLarge)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

        val scrollState = rememberScrollState()

        LaunchedEffect(key1 = state.maxScrollingValue) {
            delay(500)
            if (state.maxScrollingValue > 0) {
                scrollState.animateScrollTo(
                    value = state.maxScrollingValue,
                    animationSpec = infiniteRepeatable(
                        tween(
                            durationMillis = (state.maxScrollingValue - state.scrollValue) * 50_000 / state.maxScrollingValue,
                            easing = LinearEasing,
                            delayMillis = 1000
                        )
                    )
                )
            }
        }
        // Update the maxScrollingValue
        LaunchedEffect(key1 = scrollState.maxValue) {
            event(HomeEvent.UpdateMaxScrollingValue(scrollState.maxValue))
        }
        // Save the state of the scrolling position
        LaunchedEffect(key1 = scrollState.value) {
            event(HomeEvent.UpdateScrollValue(scrollState.value))
        }


        NewsList(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraLarge),
            listOfNews = listOfNews,
            onClick = {
                navigateToDetails(it)
            }
        )
    }
}

