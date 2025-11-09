package com.example.esigram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R
import com.example.esigram.domains.models.User

@Composable
fun ContactBanner(
    onClickCall: () -> Unit,
    onClickCallCamera: () -> Unit,
    onBackClick: () -> Unit,
    user: User
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back button",
                    tint = Color.Black
                )
            }

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = Color.Black,
                modifier = Modifier
                    .size(52.dp)
                    .padding(start = 4.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = "${user.forename} ${user.name}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                if (user.isOnline) {
                    Text(
                        text = "Online",
                        fontSize = 14.sp,
                        color = Color(0xFF4CAF50)
                    )
                } else {
                    Text(
                        text = "Offline",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            IconButton(
                onClick = onClickCall,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEAEAEA))
            ) {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = "Call",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onClickCallCamera,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEAEAEA))
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_videocam_24),
                    contentDescription = "Camera call",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }

        }
    }
}

@Composable
@Preview
fun ContactBannerPreview() {
    ContactBanner(
        onClickCall = {},
        onClickCallCamera = {},
        onBackClick = {},
        user = User(
            id = "kjqopkdqoskdpqosds",
            forename = "Léna",
            name = "Mabille",
            isOnline = true
        )
    )
}
