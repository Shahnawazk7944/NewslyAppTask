package com.example.newsly.presentation.news_navigator

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsly.R
import com.example.newsly.domain.model.News
import com.example.newsly.presentation.bookmark.BookmarkScreen
import com.example.newsly.presentation.bookmark.BookmarkViewModel
import com.example.newsly.presentation.details.NewsDetailsEvent
import com.example.newsly.presentation.details.NewsDetailsScreen
import com.example.newsly.presentation.details.NewsDetailsViewModel
import com.example.newsly.presentation.home.HomeScreen
import com.example.newsly.presentation.home.HomeViewModel
import com.example.newsly.presentation.news_navigator.components.BottomNavigationItem
import com.example.newsly.presentation.news_navigator.components.NewslyBottomNavigation
import com.example.newsly.presentation.nvgraph.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewslyNavigator(
    homeViewModel : HomeViewModel
) {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.home_icon, text = "Home"),
            BottomNavigationItem(icon = R.drawable.fav_icon, text = "Bookmarks")
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.BookmarkScreen.route -> 1
            else -> 0
        }
    }


    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(
       containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewslyBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTap(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val listOfNews = homeViewModel.news.collectAsLazyPagingItems()

                val state by homeViewModel.state
                HomeScreen(
                    listOfNews = listOfNews,
                    navigateToDetails = { news ->
                        navigateToDetails(
                            navController = navController,
                            news = news
                        )
                    },
                    state = state,
                    event = { event ->
                        homeViewModel.onEvent(event)
                    }
                )
            }

            composable(route = Route.DetailsScreen.route) {
                val viewModel: NewsDetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onEvent(NewsDetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<News?>("news")
                    ?.let { news ->
                        NewsDetailsScreen(
                            news = news,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() })
                    }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state = state, navigateToDetails = { news ->
                    navigateToDetails(navController = navController, news = news)
                })
            }
        }
    }
}

private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, news: News) {
    navController.currentBackStackEntry?.savedStateHandle?.set("news", news)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}











