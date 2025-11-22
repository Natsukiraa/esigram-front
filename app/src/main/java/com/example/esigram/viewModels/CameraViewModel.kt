package com.example.esigram.viewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.camera.core.SurfaceRequest
import androidx.camera.video.VideoRecordEvent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.CapturedMedia
import com.example.esigram.services.CameraService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CameraUiState(
    val capturedMedia: CapturedMedia? = null,
    val isRecording: Boolean = false,
    val surfaceRequest: SurfaceRequest? = null
)

class CameraViewModel(application: Application) : AndroidViewModel(application) {

    private val cameraService = CameraService(application)
    private var recordingJob: Job? = null

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            cameraService.surfaceRequestFlow.collect { request ->
                _uiState.update { it.copy(surfaceRequest = request) }
            }
        }
    }

    fun bindCamera(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            cameraService.bindCamera(lifecycleOwner)
        }
    }

    fun onMediaSelected(uri: Uri?) {
        if (uri == null) {
            Log.d("CameraViewModel", "Media selection cancelled.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val context = getApplication<Application>()
                val mimeType = context.contentResolver.getType(uri)
                val isVideo = mimeType?.startsWith("video") == true

                Log.d("CameraViewModel", "Media selected: $uri, isVideo: $isVideo")

                _uiState.update {
                    it.copy(capturedMedia = CapturedMedia(uri, isVideo))
                }
            } catch (e: Exception) {
                Log.e("CameraViewModel", "Error processing selected media", e)
            }
        }
    }

    fun onCapturePhoto() {
        viewModelScope.launch {
            val result = cameraService.capturePhoto()
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(capturedMedia = CapturedMedia(result.getOrThrow(), isVideo = false))
                }
            } else {
                Log.e("CameraViewModel", "Photo capture failed", result.exceptionOrNull())
            }
        }
    }

    fun onToggleRecording() {
        // Start recording
        _uiState.update { it.copy(isRecording = true) }
        recordingJob = viewModelScope.launch {
            cameraService.startRecording().collect { event ->
                when (event) {
                    is VideoRecordEvent.Finalize -> {
                        if (!event.hasError()) {
                            Log.d(
                                "CameraViewModel",
                                "Video recorded: ${event.outputResults.outputUri}"
                            )
                            _uiState.update {
                                it.copy(
                                    isRecording = false,
                                    capturedMedia = CapturedMedia(
                                        event.outputResults.outputUri,
                                        isVideo = true
                                    )
                                )
                            }
                            cameraService.stopRecording()
                        } else {
                            Log.e("CameraViewModel", "Video recording error", event.cause)
                            _uiState.update { it.copy(isRecording = false) }
                        }
                    }
                }
            }
        }

    }

    fun onCancelMedia() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState.value.capturedMedia?.uri?.let {
                    getApplication<Application>().contentResolver.delete(it, null, null)
                }
            } catch (e: Exception) {
                Log.e("CameraViewModel", "Error deleting media", e)
            }
            _uiState.update { it.copy(capturedMedia = null) }
        }
    }

    fun onSendHandled() {
        Log.d(
            "CameraViewModel",
            "Send handled, clearing media: ${uiState.value.capturedMedia?.uri}"
        )
        // Just go back to the camera
        _uiState.update { it.copy(capturedMedia = null) }
    }

    override fun onCleared() {
        cameraService.unbindCamera()
        super.onCleared()
    }
}