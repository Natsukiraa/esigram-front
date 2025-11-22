package com.example.esigram.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.esigram.domains.models.Story
import com.example.esigram.domains.repositories.StoryRepository

class StoryViewModel : ViewModel() {
    private val repo = StoryRepository()
    private val _stories = mutableStateListOf<Story>().apply { addAll(repo.getAll()) }
    val stories: List<Story> = _stories
}