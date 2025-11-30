package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.domains.models.User

@Composable
fun FriendItem(
    friend: User,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        ) {
            // Tu mets Coil ici si tu as une URL d’image
            // Image(painter = rememberAsyncImagePainter(friend.avatarUrl), ...)
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Nom
        Text(
            text = friend.username,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        // Checkbox de sélection
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendItemPreview() {
    // Fake user
    val user = User(
        id = "1",
        username = "Arthur",
        email = "arthur@example.com",
        profilePicture = null // si tu en as une
    )

    // État local pour simuler la sélection
    var selected by remember { mutableStateOf(false) }

    FriendItem(
        friend = user,
        isSelected = selected,
        onClick = { selected = !selected }
    )
}