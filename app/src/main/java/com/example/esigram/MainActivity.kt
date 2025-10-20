package com.example.esigram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.provider.FirebaseProvider
import com.example.esigram.ui.screens.ConversationListScreen
import com.example.esigram.ui.theme.EsigramTheme
import com.example.esigram.viewModels.ConversationViewModel

class MainActivity : ComponentActivity() {
    private val conversationViewModel: ConversationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseProvider.init(this)
        setContent {
            EsigramTheme {
                ConversationListScreen(
                    conversationViewModel = conversationViewModel,
                    onOpenMessage = {}
                )
                /*
                Surface(
                    modifier = Modifier.padding(12.dp)
                ) {
                    NavGraph(
                        convViewModel = conversationViewModel
                    )
                }
                 */
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