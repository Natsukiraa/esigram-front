package com.example.esigram.ui.components.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.esigram.R
import com.example.esigram.domains.models.User
import com.example.esigram.ui.components.conversations.ProfileImage
import com.example.esigram.ui.components.utils.ExpandableText

@Composable
fun FriendProfileModal(
    user: User,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp,
            modifier = Modifier.width(280.dp)
        ) {

            Column {
                Box(
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                ) {
                    ProfileImage(
                        url = user.profilePicture?.signedUrl,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = user.username,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }


                }

                user.description?.let {
                    ExpandableText(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FriendProfileModalPreview() {
    val fakeUser = User(
        id = "1",
        username = "Alice",
        description = "This is a sample description for Alice. She loves programming and enjoys hiking during the weekends.",
        email = "test@gmail.com"
    )

    FriendProfileModal(
        user = fakeUser,
        onDismiss = {}
    )
}