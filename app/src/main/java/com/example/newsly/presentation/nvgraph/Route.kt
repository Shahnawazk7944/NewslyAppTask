package com.example.newsly.presentation.nvgraph

sealed class Route(
    val route: String
){
    object HomeScreen : Route(route = "homeScreen")
    object BookmarkScreen : Route(route = "bookmarkScreen")
    object DetailsScreen : Route(route = "detailsScreen")
    object NewsNavigation : Route(route = "newsNavigation")
    object NewslyStartDestination : Route(route = "newslyStartDestination")
}
