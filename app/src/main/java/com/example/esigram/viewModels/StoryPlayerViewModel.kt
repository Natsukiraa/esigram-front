package com.example.esigram.viewModels
import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.Story
import com.example.esigram.domains.usecase.story.StoryUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoryPlayerViewModel(
    private val useCases: StoryUseCases
) : ViewModel() {

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories = _stories.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun loadStoriesForUser(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = useCases.getStoriesUseCase(userId)
                _stories.value = result.data
                _currentIndex.value = 0
            } catch (e: Exception) {
                Log.e("StoryPlayerViewModel", "Error loading stories: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun nextStory(onFinished: () -> Unit) {
        val currentList = _stories.value
        if (currentList.isEmpty()) return

        if (_currentIndex.value < currentList.lastIndex) {
            _currentIndex.value += 1
        } else {
            onFinished()
        }
    }

    fun prevStory() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
        } else {
            _currentIndex.value = 0
        }
    }
}