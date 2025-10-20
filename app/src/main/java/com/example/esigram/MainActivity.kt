package com.example.esigram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.models.Conversation
import com.example.esigram.models.Message
import com.example.esigram.models.User
import com.example.esigram.ui.components.ConversationItem
import com.example.esigram.ui.screens.ConversationListScreen
import com.example.esigram.ui.theme.EsigramTheme
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.StoryViewModel
import com.example.mytodoz.NavGraph
import java.time.Duration
import java.time.Instant

class MainActivity : ComponentActivity() {
    private val conversationViewModel: ConversationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EsigramTheme {
                Surface(
                    modifier = Modifier.padding(12.dp)
                ) {
                    NavGraph(
                        convViewModel = conversationViewModel
                    )
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