package com.example.esigram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.models.Conversation
import com.example.esigram.models.Message
import com.example.esigram.models.User
import com.example.esigram.ui.components.ConversationItem
import com.example.esigram.ui.theme.EsigramTheme
import java.time.Duration
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EsigramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val user = User(
                        id = 1,
                        pseudo = "Fantom",
                        avatarUrl = "https://randomuser.me/api/portraits/men/1.jpg"
                    )

                    val message = Message(
                        id = 1,
                        text = "coucou",
                        Instant.now().minus(Duration.ofDays(4))
                    )
                    val conversation = Conversation(
                        1,
                        participants = mutableListOf(user),
                        lastMessage = message,
                        createdAt = Instant.now(),
                        unreadCount = 2)
                    ConversationItem(conversation, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EsigramTheme {
        Greeting("Android")
    }
}