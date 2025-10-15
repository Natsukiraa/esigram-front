package com.example.esigram.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    color: Int = R.color.primaryColor,
    text: String = "",
    fontSize: Int = 12
) {

    Box(modifier = modifier
        .clip(CircleShape)
        .background(colorResource(color)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text, fontSize = fontSize.sp,
            color = colorResource(id = R.color.white)
        )
    }

}


@Preview
@Composable
fun BadgePreview() {
    Badge(modifier = Modifier.size(24.dp), text = "3")
}