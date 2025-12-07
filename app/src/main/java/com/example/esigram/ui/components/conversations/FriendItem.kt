package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
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
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else Color.Transparent
    val borderStroke = if (isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .then(if (borderStroke != null) Modifier.border(borderStroke, RoundedCornerShape(12.dp)) else Modifier)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(
            url = friend.profilePicture?.signedUrl,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = friend.username,
            fontSize = 17.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendItemPreview() {
    MaterialTheme {
        Column(Modifier.padding(16.dp)) {
            val user = User(
                id = "1",
                username = "Arthur",
                email = "arthur@example.com",
                profilePicture = null
            )

            var selected by remember { mutableStateOf(false) }

            FriendItem(
                friend = user,
                isSelected = selected,
                onClick = { selected = !selected }
            )

            Spacer(Modifier.height(8.dp))

            FriendItem(
                friend = User(id = "2", username = "Léna", email = "lena@example.com", profilePicture = null),
                isSelected = true,
                onClick = { }
            )
        }
    }
}