package com.example.esigram.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.esigram.domains.models.CapturedMedia
import com.example.esigram.ui.components.camera.CameraCaptureScreen
import com.example.esigram.ui.components.camera.MediaPreviewScreen
import com.example.esigram.viewModels.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel,
    onSend: (CapturedMedia) -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )

    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.onMediaSelected(uri)
    }

    if (permissionState.allPermissionsGranted) {
        LaunchedEffect(Unit) {
            viewModel.bindCamera(lifecycleOwner)
        }

        if (uiState.capturedMedia == null) {
            CameraCaptureScreen(
                surfaceRequest = uiState.surfaceRequest,
                isRecording = uiState.isRecording,
                onCapture = viewModel::onCapturePhoto,
                onToggleRecord = viewModel::onToggleRecording,
                onSelectFromGallery = {
                    pickMediaLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageAndVideo
                        )
                    )
                }
            )
        } else {
            val mediaToPreview = uiState.capturedMedia!!
            MediaPreviewScreen(
                media = mediaToPreview,
                onCancel = viewModel::onCancelMedia,
                onSend = {
                    onSend(mediaToPreview)
                    viewModel.onSendHandled()
                }
            )
        }

    } else {
        LaunchedEffect(Unit) {
            permissionState.launchMultiplePermissionRequest()
        }
    }
}