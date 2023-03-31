package com.example.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.compose.rememberNavController
import com.example.login.Chats.NoChatsScreen
import com.example.login.Login.LoginPage
import com.example.login.Login.RegisterPage
import com.example.login.ui.theme.LoginTheme

class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            LoginTheme {
                // A surface container using the 'background' color from the theme
                sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
                val username = remember {
                    mutableStateOf("")
                }
                val pwd = remember {
                    mutableStateOf("")
                }
                username.value = sharedPreferences.getString("USERNAME", "").toString()
                pwd.value = sharedPreferences.getString("SECRET", "").toString()

                val navController = rememberNavController()
                navi(navController = navController, sharedPreferences)
            }
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
                val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                view.updatePadding(bottom = bottom)
                insets
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginTheme {

    }
}