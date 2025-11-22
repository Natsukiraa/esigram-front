package com.example.esigram.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.domains.models.Message
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MessageBox(
    message: Message,
    onHold: (String) -> Unit,
) {

    val bg = when (message.colorIndex % 2) {
        0 -> Color(0xff5167f1)
        1 -> Color(0xFFFFFFFF)
        else -> Color(0xFFFF6263)
    }

    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    val formattedTime = message.createdAt
        .atZone(ZoneId.systemDefault())
        .format(formatter)

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = if (message.colorIndex % 2 == 0)
            Alignment.End
        else
            Alignment.Start
    ) {
        Button(
            onClick = { onHold(message.id) },
            colors = ButtonDefaults.buttonColors(
                containerColor = bg,
                contentColor = if (message.colorIndex % 2 == 1) Color.Black else Color.White
            ),
            shape = if (message.colorIndex % 2 == 0)
                RoundedCornerShape(12.dp, 0.dp, 12.dp, 12.dp)
            else
                RoundedCornerShape(0.dp, 12.dp, 12.dp, 12.dp)

        ) {
            Box {
                Text(
                    text = message.content,
                    fontSize = 16.sp
                )
            }

        }


        Row {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (message.colorIndex % 2 == 1) Arrangement.Start else Arrangement.End
            ) {

                Text(
                    modifier = Modifier
                        .padding(end = 12.dp),
                    text = formattedTime
                )

                if (message.colorIndex % 2 == 0) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        tint = if (message.seen) Color(0xff5167f1) else Color.White,
                        contentDescription = "Seen"
                    )
                }
            }

        }
    }


}

@Composable
@Preview
fun MessageBoxPreview() {
    MessageBox(
        message = Message(
            id = "doksqpdqsod",
            content = "Ceci est un test",
            colorIndex = 1,
            seen = false,
            authorId = "user"
        ),
        onHold = {}
    )
}