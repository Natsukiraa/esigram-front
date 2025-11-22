package com.example.esigram.ui.components.story

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R
import com.example.esigram.domains.models.Media
import com.example.esigram.models.CorrectUserToDelete
import com.example.esigram.ui.components.ProfileImage

@Composable
fun UserStoryBubble(
    onClick: () -> Unit,
    author: CorrectUserToDelete,
    isMe: Boolean = false
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(70.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 3.dp,
                    brush = if (!author.alreadyViewedStories) {
                        Brush.linearGradient(
                            colors = listOf(colorResource(id = R.color.primaryColor), Color.Cyan),
                            start = androidx.compose.ui.geometry.Offset(50f, 50f),
                            end = androidx.compose.ui.geometry.Offset(300f, 300f)
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(Color.Gray, Color.LightGray)
                        )
                    },
                    shape = CircleShape
                ),
            shape = CircleShape,
            color = Color.Transparent
        ) {
            ProfileImage(
                url = author.profilePicture?.signedUrl,
                modifier = Modifier
                    .padding(6.dp)
                    .clip(CircleShape)
            )
        }

        if (isMe) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text("+", color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

@Composable
@Preview
fun UserStoryBubblePreview() {
    val user = CorrectUserToDelete(
        id = "1",
        username = "johndoe",
        email = "jonhdoe@test.fr",
        description = "Just a test user",
        alreadyViewedStories = false,
        profilePicture = Media(
            id = "media1",
            signedUrl = "https://cataas.com/cat"
        )
    )

    UserStoryBubble(
        onClick = {},
        author = user,
        isMe = true
    )
}
