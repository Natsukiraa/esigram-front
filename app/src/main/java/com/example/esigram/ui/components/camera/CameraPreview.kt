//package com.example.esigram.ui.components.camera
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.ContentValues
//import android.content.Context
//import android.net.Uri
//import android.provider.MediaStore
//import android.util.Log
//import androidx.camera.compose.CameraXViewfinder
//import androidx.camera.core.Camera
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.core.Preview
//import androidx.camera.core.SurfaceRequest
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.lifecycle.awaitInstance
//import androidx.camera.video.MediaStoreOutputOptions
//import androidx.camera.video.Quality
//import androidx.camera.video.QualitySelector
//import androidx.camera.video.Recorder
//import androidx.camera.video.Recording
//import androidx.camera.video.VideoCapture
//import androidx.camera.video.VideoRecordEvent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.Send
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import androidx.media3.common.MediaItem
//import androidx.media3.common.Player
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.ui.PlayerView
//import androidx.media3.common.AudioAttributes
//import androidx.media3.common.C
//import coil.compose.AsyncImage
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.isGranted
//import com.google.accompanist.permissions.rememberPermissionState
//import kotlinx.coroutines.flow.MutableStateFlow
//
//data class CapturedMedia(
//    val uri: Uri,
//    val isVideo: Boolean
//)
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun CameraScreen() {
//    val cameraPermissionState = rememberPermissionState(
//        Manifest.permission.CAMERA
//    )
//
//    val audioPermissionState = rememberPermissionState(
//        Manifest.permission.RECORD_AUDIO
//    )
//
//    // This state will hold the URI of the captured media
//    // If null, show camera. If non-null, show preview.
//    var capturedMedia by remember {
//        mutableStateOf<CapturedMedia?>(null)
//    }
//
//    val context = LocalContext.current
//
//    if (cameraPermissionState.status.isGranted && audioPermissionState.status.isGranted) {
//
//        if (capturedMedia == null) {
//            // State 1: Show Camera Preview
//            CameraPreview(
//                onMediaCaptured = { media ->
//                    capturedMedia = media
//                }
//            )
//        } else {
//            // State 2: Show Media Preview
//            MediaPreviewScreen(
//                media = capturedMedia!!,
//                onCancel = {
//                    // User pressed "Cancel", delete the file and go back to camera
//                    try {
//                        context.contentResolver.delete(capturedMedia!!.uri, null, null)
//                    } catch (e: Exception) {
//                        Log.e("CameraScreen", "Error deleting media", e)
//                    }
//                    capturedMedia = null
//                },
//                onSend = {
//                    // User pressed "Send"
//                    // TODO: Add your logic (e.g., upload file, save to profile)
//
//                    // For now, just go back to the camera
//                    capturedMedia = null
//                }
//            )
//        }
//
//    } else {
//        LaunchedEffect(Unit) {
//            cameraPermissionState.launchPermissionRequest()
//            audioPermissionState.launchPermissionRequest()
//        }
//    }
//}
//
//@Composable
//private fun CameraPreview(
//    onMediaCaptured: (CapturedMedia) -> Unit // Callback to notify parent
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    var camera by remember { mutableStateOf<Camera?>(null) }
//    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
//    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
//    var activeRecording by remember { mutableStateOf<Recording?>(null) }
//
//    val surfaceRequests = remember { MutableStateFlow<SurfaceRequest?>(null) }
//    val surfaceRequest by surfaceRequests.collectAsState(initial = null)
//
//    // Bind all use cases
//    LaunchedEffect(Unit) {
//        val provider = ProcessCameraProvider.awaitInstance(context)
//
//        val preview = Preview.Builder().build().apply {
//            setSurfaceProvider { req -> surfaceRequests.value = req }
//        }
//
//        imageCapture = ImageCapture.Builder()
//            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//            .build()
//
//        val recorder = Recorder.Builder()
//            .setQualitySelector(QualitySelector.from(Quality.FHD))
//            .build()
//        videoCapture = VideoCapture.withOutput(recorder)
//
//        provider.unbindAll() // Unbind previous use cases
//        camera = provider.bindToLifecycle(
//            lifecycleOwner,
//            CameraSelector.DEFAULT_BACK_CAMERA,
//            preview,
//            imageCapture!!,
//            videoCapture!!
//        )
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//
//        // Camera preview
//        surfaceRequest?.let { request ->
//            CameraXViewfinder(
//                surfaceRequest = request,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//        // UI controls
//        CameraControls(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 32.dp),
//            onCapture = {
//                capturePhoto(context, imageCapture, onMediaCaptured)
//            },
//            toggleRecord = {
//                activeRecording = toggleRecording(
//                    context,
//                    videoCapture,
//                    activeRecording,
//                    onMediaCaptured // Pass the callback
//                )
//            },
//            activeRecording = activeRecording
//        )
//    }
//}
//
///**
// * A new composable to display the captured image or video.
// */
//@Composable
//fun MediaPreviewScreen(
//    media: CapturedMedia,
//    onCancel: () -> Unit,
//    onSend: () -> Unit
//) {
//    val context = LocalContext.current
//
//    Box(modifier = Modifier.fillMaxSize()) {
//
//        if (media.isVideo) {
//            // --- VIDEO PREVIEW ---
//            val exoPlayer = remember(media.uri) {
//
//                // 1. Create Audio Attributes
//                // This tells the system what kind of audio you are playing.
//                val audioAttributes = AudioAttributes.Builder()
//                    .setUsage(C.USAGE_MEDIA) // Defines the purpose: media playback
//                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE) // Describes the content
//                    .build()
//
//                ExoPlayer.Builder(context).build().apply {
//                    setMediaItem(MediaItem.fromUri(media.uri))
//                    setAudioAttributes(audioAttributes, true)
//                    setHandleAudioBecomingNoisy(true)
//                    prepare()
//                    playWhenReady = true
//                    repeatMode = Player.REPEAT_MODE_ONE
//                    volume = 1f
//                }
//            }
//
//            // Release the player when the composable is disposed
//            DisposableEffect(Unit) {
//                onDispose { exoPlayer.release() }
//            }
//
//            // Use AndroidView to host the Media3 PlayerView
//            AndroidView(
//                factory = {
//                    PlayerView(it).apply {
//                        setUseController(false)
//                    }
//                },
//                update = { view ->
//                    view.player = exoPlayer
//                },
//                modifier = Modifier.fillMaxSize()
//            )
//
//        } else {
//            // --- IMAGE PREVIEW ---
//            AsyncImage(
//                model = media.uri,
//                contentDescription = "Captured media preview",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Fit
//            )
//        }
//
//        // --- UI CONTROLS ---
//
//        // Cancel Button (Top Start)
//        IconButton(
//            onClick = onCancel,
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(16.dp)
//                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
//        ) {
//            Icon(Icons.Default.Close, contentDescription = "Cancel", tint = Color.White)
//        }
//
//        // Send Button (Bottom End)
//        IconButton(
//            onClick = onSend,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp)
//                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
//        ) {
//            Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
//        }
//    }
//}
//private fun capturePhoto(
//    context: Context,
//    imageCapture: ImageCapture?,
//    onMediaCaptured: (CapturedMedia) -> Unit // Changed to use callback
//) {
//    Log.d("CameraPreview", "Capturing photo...")
//    val capture = imageCapture ?: return
//
//    val name = "IMG_${System.currentTimeMillis()}.jpg"
//    val contentValues = ContentValues().apply {
//        put(MediaStore.Images.Media.DISPLAY_NAME, name)
//        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//    }
//
//    val outputOptions = ImageCapture.OutputFileOptions.Builder(
//        context.contentResolver,
//        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//        contentValues
//    ).build()
//
//    capture.takePicture(
//        outputOptions,
//        ContextCompat.getMainExecutor(context),
//        object : ImageCapture.OnImageSavedCallback {
//            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                Log.d("CameraPreview", "Photo saved: ${output.savedUri}")
//                output.savedUri?.let {
//                    // Notify the caller with the new media URI
//                    onMediaCaptured(CapturedMedia(it, isVideo = false))
//                }
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                Log.e("CameraPreview", "Photo capture failed", exception)
//                // Handle error
//            }
//        }
//    )
//}
//
//@SuppressLint("MissingPermission")
//private fun toggleRecording(
//    context: Context,
//    videoCapture: VideoCapture<Recorder>?,
//    currentRecording: Recording?,
//    onMediaCaptured: (CapturedMedia) -> Unit // Changed to use callback
//): Recording? {
//    Log.d("CameraPreview", "Toggling recording...")
//    val capture = videoCapture ?: return null
//
//    if (currentRecording != null) {
//        currentRecording.stop()
//        return null
//    }
//
//    val name = "VID_${System.currentTimeMillis()}.mp4"
//    val contentValues = ContentValues().apply {
//        put(MediaStore.Video.Media.DISPLAY_NAME, name)
//    }
//
//    val outputOptions = MediaStoreOutputOptions.Builder(
//        context.contentResolver,
//        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//    ).setContentValues(contentValues).build()
//
//    return capture.output
//        .prepareRecording(context, outputOptions)
//        .withAudioEnabled()
//        .start(ContextCompat.getMainExecutor(context)) { event ->
//            // Handle recording events
//            when (event) {
//                is VideoRecordEvent.Finalize -> {
//                    if (!event.hasError()) {
//                        Log.d("CameraPreview", "Video saved: ${event.outputResults.outputUri}")
//                        event.outputResults.outputUri?.let {
//                            // Notify the caller with the new media URI
//                            onMediaCaptured(CapturedMedia(it, isVideo = true))
//                        }
//                    } else {
//                        Log.e("CameraPreview", "Video recording error", event.cause)
//                    }
//                }
//            }
//        }
//}