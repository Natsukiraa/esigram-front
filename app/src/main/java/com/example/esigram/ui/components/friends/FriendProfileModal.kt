package com.example.esigram.ui.components.friends

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
    isAlreadyFriend: Boolean = false,
    onDismiss: () -> Unit,
    onMessageClick: () -> Unit,
    onAddFriend: () -> Unit
) {
    val context = LocalContext.current

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
                        color = Color.Black.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = user.username,
                            style = MaterialTheme.typography.titleMedium,
                            color = colorResource(R.color.white),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (!isAlreadyFriend) {
                        IconButton(onClick = onAddFriend) {
                            Icon(
                                painter = painterResource(id = R.drawable.person_add_24px),
                                contentDescription = "Add Friend",
                                tint = colorResource(R.color.primaryColor)
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            Toast.makeText(
                                context,
                                "You are already friends with ${user.username}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.person_check_24px),
                                contentDescription = "Already Friends",
                                tint = colorResource(R.color.textSecondary)
                            )
                        }
                    }

                    IconButton(onClick = onMessageClick) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Message",
                            tint = colorResource(R.color.primaryColor)
                        )
                    }
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
        onDismiss = {},
        onMessageClick = {},
        onAddFriend = {}
    )
}