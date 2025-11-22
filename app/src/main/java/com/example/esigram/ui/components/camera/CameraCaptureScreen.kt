package com.example.esigram.ui.components.camera

import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.SurfaceRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CameraCaptureScreen(
    surfaceRequest: SurfaceRequest?,
    isRecording: Boolean,
    onCapture: () -> Unit,
    onToggleRecord: () -> Unit,
    onSelectFromGallery: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (surfaceRequest != null) {
            CameraXViewfinder(
                surfaceRequest = surfaceRequest,
                modifier = Modifier.fillMaxSize()
            )
        }

        CameraControls(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            onCapture = onCapture,
            toggleRecord = onToggleRecord,
            activeRecording = isRecording,
            onSelectFromGallery = onSelectFromGallery
        )
    }
}