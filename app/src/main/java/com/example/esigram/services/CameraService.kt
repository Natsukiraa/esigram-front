package com.example.esigram.services

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class CameraService(private val context: Context) {

    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var activeRecording: Recording? = null

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequestFlow = _surfaceRequest.asStateFlow()

    suspend fun bindCamera(lifecycleOwner: LifecycleOwner) {
        cameraProvider = ProcessCameraProvider.awaitInstance(context)

        val preview = Preview.Builder().build().apply {
            setSurfaceProvider { req -> _surfaceRequest.value = req }
        }

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.FHD))
            .build()
        videoCapture = VideoCapture.withOutput(recorder)

        cameraProvider?.unbindAll()
        cameraProvider?.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            imageCapture,
            videoCapture
        )
    }

    fun unbindCamera() {
        cameraProvider?.unbindAll()
    }

    suspend fun capturePhoto(): Result<Uri> {
        val capture = imageCapture
            ?: return Result.failure(IllegalStateException("ImageCapture not initialized"))

        return suspendCancellableCoroutine { continuation ->
            val name = "IMG_${System.currentTimeMillis()}.jpg"
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val outputOptions = ImageCapture.OutputFileOptions.Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

            capture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        Log.d("CameraService", "Photo saved: ${output.savedUri}")
                        continuation.resume(Result.success(output.savedUri!!))
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e("CameraService", "Photo capture failed", exception)
                        continuation.resume(Result.failure(exception))
                    }
                }
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun startRecording(): Flow<VideoRecordEvent> {
        val capture = videoCapture ?: throw IllegalStateException("VideoCapture not initialized")
        stopRecording() // Stop any previous recording

        val name = "VID_${System.currentTimeMillis()}.mp4"
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
        }

        val outputOptions = MediaStoreOutputOptions.Builder(
            context.contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        ).setContentValues(contentValues).build()

        // callbackFlow converts callback-based APIs into a Flow
        return callbackFlow {
            activeRecording = capture.output
                .prepareRecording(context, outputOptions)
                .withAudioEnabled()
                .start(ContextCompat.getMainExecutor(context)) { event ->
                    trySend(event) // Send event to the Flow
                    if (event is VideoRecordEvent.Finalize) {
                        close() // Close the flow when recording is finalized
                    }
                }

            // Await cancellation to stop the recording
            awaitClose {
                stopRecording()
            }
        }
    }

    fun stopRecording() {
        activeRecording?.stop()
        activeRecording = null
    }
}