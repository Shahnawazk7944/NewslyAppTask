package com.example.newsly.presentation.nvgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.newsly.presentation.home.HomeViewModel
import com.example.newsly.presentation.news_navigator.NewslyNavigator


@Composable
fun NavGraph(
) {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = homeViewModel.startDestination) {

        navigation(
            route = homeViewModel.startDestination,
            startDestination = Route.HomeScreen.route
        ) {
            composable(route = Route.HomeScreen.route) {
                NewslyNavigator(
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}