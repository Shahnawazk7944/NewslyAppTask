package com.example.newsly.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.domain.usecases.NewslyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newslyUseCases: NewslyUseCases
) : ViewModel() {


    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
        getBookmarkedNews()
    }

    private fun getBookmarkedNews() {
        newslyUseCases.getBookmarkedNews().onEach {
            _state.value = _state.value.copy(listOfBookmarkedNews = it.asReversed())
        }.launchIn(viewModelScope)
    }

}