package com.example.esigram

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.repositories.AuthRepository
import com.example.esigram.ui.theme.EsigramTheme

class MainActivity : ComponentActivity() {
    private val repository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verify if theres a logged user
        val user = repository.getCurrentUser()
        if(user == null){
            // If not, redirect to sign in
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            EsigramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = user.email ?: "No user logged in",
                        modifier = Modifier.padding(innerPadding),
                        repository = repository
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, repository: AuthRepository) {
    val context = LocalContext.current

    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(
            onClick = {
                // Sign out + redirect to sign in
                repository.signOut()
                context.startActivity(Intent(context, AuthActivity::class.java))
                if(context is ComponentActivity) {
                    context.finish()
                }
            }
        ) {
            Text("Sign out")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EsigramTheme {
        Greeting("Android", Modifier, AuthRepository())
    }
}