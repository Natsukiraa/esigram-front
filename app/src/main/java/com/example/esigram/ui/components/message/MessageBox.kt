package com.example.esigram.ui.components.message

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.domains.models.Message
import com.example.esigram.networks.RetrofitInstance
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageBox(
    message: Message,
    onHold: (String) -> Unit,
) {
    val haptics = LocalHapticFeedback.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val isUserMe = message.colorIndex % 2 == 0

    val bg = if (isUserMe) Color(0xff5167f1) else Color(0xFFFFFFFF)
    val contentColor = if (isUserMe) Color.White else Color.Black
    val shape = if (isUserMe)
        RoundedCornerShape(12.dp, 0.dp, 12.dp, 12.dp)
    else
        RoundedCornerShape(0.dp, 12.dp, 12.dp, 12.dp)

    val formatter = remember {
        DateTimeFormatter.ofPattern("dd/MM HH:mm")
            .withLocale(Locale.FRANCE)
    }

    val formattedTime = try {
        message.createdAt
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    } catch (e: Exception) {
        ""
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUserMe) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = shape,
            color = bg,
            shadowElevation = 2.dp,
            modifier = Modifier
                .widthIn(max = screenWidth * 0.75f)
                .combinedClickable(
                    onClick = { },
                    onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        onHold(message.id)
                    }
                )
        ) {
            MessageBubble(
                message = message,
                contentColor = contentColor,
                apiUrl = RetrofitInstance.BASE_URL
            )
        }

        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isUserMe) Arrangement.End else Arrangement.Start
        ) {
            Text(
                text = formattedTime,
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(end = 4.dp)
            )

            if (isUserMe) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Seen status",
                    tint = if (message.seen) Color(0xff5167f1) else Color.Gray,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}