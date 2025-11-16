package com.example.esigram.ui.components.friends

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.domains.models.FriendRequest
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.ui.components.ProfileImage
import java.time.Instant

@Composable
fun InboundFriendRequestItem(
    request: FriendRequest,
    onAcceptClick: (FriendRequest) -> Unit,
    onDeclineClick: (FriendRequest) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                url = request.userAsking?.profilePicture?.signedUrl, modifier = Modifier.size(48.dp).clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))


            Column(modifier = Modifier.weight(1f)) {
                Text(text = request.userAsking?.username ?: "", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = request.userAsking?.email ?: "", style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }


            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilledIconButton(
                    onClick = { onAcceptClick(request) },
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Accept")
                }

                OutlinedIconButton(
                    onClick = { onDeclineClick(request) },
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Decline")
                }
            }
        }
    }

}

@Composable
@Preview
fun FriendRequestItemPreview() {
    val fakeRequest = FriendRequest(
        id = "1",
        userAsked = TmpUser(
            id = "2", username = "John Doe", email = "a@.fr"
        ),
        userAsking = TmpUser(
            id = "3", username = "Jane Smith", email = "b@.fr"
        ),
        status = FriendRequest.FriendStatus.PENDING,
    )

    InboundFriendRequestItem(request = fakeRequest, onAcceptClick = {}, onDeclineClick = {})
}