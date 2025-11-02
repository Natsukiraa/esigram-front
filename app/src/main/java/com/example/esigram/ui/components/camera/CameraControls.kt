package com.example.esigram.ui.components.camera

import androidx.camera.video.Recording
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.esigram.R

@Composable
fun CameraControls(
    modifier: Modifier = Modifier,
    onCapture: () -> Unit,
    toggleRecord: () -> Unit,
    activeRecording: Recording?,
) {

    Row (
        modifier = modifier
    ){
        IconButton(
            onClick = { onCapture() }
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(R.drawable.photo_camera_24px),
                contentDescription = "Microphone"
            )
        }


        Spacer(modifier = Modifier.width(32.dp))


        IconButton(
            onClick = {
                toggleRecord()
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                painter = if (activeRecording == null) painterResource(R.drawable.video_camera_back_24px)
                else painterResource(R.drawable.video_camera_front_off_24px),
                contentDescription = "Microphone"
            )
        }
    }


}