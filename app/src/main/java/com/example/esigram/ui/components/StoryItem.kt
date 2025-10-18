package com.example.esigram.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.models.Story
import com.example.esigram.models.User
import com.example.esigram.ui.theme.EsigramExtraColors

@Composable
fun StoryItem(
    story: Story,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier.size(48.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ProfileImage(
                story.user.image ?: "",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, Color.Blue), CircleShape)
            )

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(EsigramExtraColors.onlineBadge)
                    .border(BorderStroke(1.dp, Color.White), CircleShape)
            )
        }

        Text(
            text = "${story.user.forename} ${story.user.name}",
            modifier = Modifier.padding(top = 4.dp),
            fontSize = 8.sp,
            fontWeight = FontWeight.Light
        )
    }
}


@Preview
@Composable
fun StoryItemPreview() {
    val story = Story(
            "1",
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        )

    StoryItem(story, Modifier.size(48.dp))
}