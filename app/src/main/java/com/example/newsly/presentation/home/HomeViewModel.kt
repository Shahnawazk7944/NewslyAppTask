package com.example.newsly.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsly.domain.usecases.NewslyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.example.newsly.presentation.nvgraph.Route
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.launch


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newslyUseCases: NewslyUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    var startDestination by mutableStateOf(Route.NewslyStartDestination.route)
        private set

    val news = newslyUseCases.getNews(
        sources = listOf("TechCrunch","apple","bbc-news")
    ).cachedIn(viewModelScope)


    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is HomeEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
        }
    }
    private fun updateScrollValue(newValue: Int){
        _state.value = state.value.copy(scrollValue = newValue)
    }
    private fun updateMaxScrollingValue(newValue: Int){
        _state.value = state.value.copy(maxScrollingValue = newValue)
    }
}