package com.example.esigram.ui.components.story

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.domains.models.TmpUser

@Composable
fun StoryList(
    me: TmpUser,
    friends: List<TmpUser>,
    onMyStoryClick: () -> Unit = {},
    onFriendStoryClick: (TmpUser) -> Unit = {},
) {
    val sortedFriends = friends
        .filter { it.hasStories }
        .sortedBy { it.alreadyViewedStories }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            UserStoryBubble(
                onClick = onMyStoryClick,
                author = me,
                isMe = true
            )
        }

        items(sortedFriends) { friend ->
            UserStoryBubble(
                onClick = { onFriendStoryClick(friend) },
                author = friend
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoryListPreview() {
    val me = TmpUser(
        id = "me",
        username = "Me",
        email = "me@mail.com"
    )

    val friends = listOf(
        TmpUser(
            "1",
            "Alice",
            "a@mail.com",
            hasStories = true,
            alreadyViewedStories = false
        ),
        TmpUser(
            "2",
            "Bob",
            "b@mail.com",
            hasStories = true,
            alreadyViewedStories = true
        ),
        TmpUser(
            "3",
            "Charlie",
            "c@mail.com",
            hasStories = true,
            alreadyViewedStories = false
        ),
        TmpUser(
            "4",
            "Diana",
            "d@mail.com",
            hasStories = true,
            alreadyViewedStories = true
        )
    )

    StoryList(me = me, friends = friends)
}
