package com.example.esigram.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.esigram.domains.usecase.story.StoryUseCases
import java.io.File

class StoryViewModel(
    private val useCases: StoryUseCases
) : ViewModel() {
    suspend fun postStory(file: File): Boolean {
        return try {
            useCases.createStoryUseCase(file)
            true
        } catch (e: Exception) {
            Log.e("StoryViewModel", "Error posting story: ${e.message}")
            false
        }
    }
}
