package com.example.newsly.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.domain.usecases.NewslyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newslyUseCases: NewslyUseCases
) : ViewModel() {


    private val _state = MutableStateFlow(BookmarkState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            delay(4000)
            getBookmarkedNews()
            _state.value = _state.value.copy(loading = false)
        }
    }

    private fun getBookmarkedNews() {
        newslyUseCases.getBookmarkedNews().onEach {
            _state.value = _state.value.copy(listOfBookmarkedNews = it.asReversed())
        }.launchIn(viewModelScope)
    }

}