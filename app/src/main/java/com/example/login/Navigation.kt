package com.example.login

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.login.Chats.ChatHistory
import com.example.login.Chats.MessageList
import com.example.login.Chats.NoChatsScreen
import com.example.login.Login.LoadingViewModel
import com.example.login.Login.LoginPage
import com.example.login.Login.RegisterPage
import com.example.login.Room.QuestionList
import com.example.login.Room.QuestionViewModel
import com.example.login.Room.Support

@Composable
fun navi(navController : NavHostController, sharedPreferences: SharedPreferences){
    val loadingViewModel:LoadingViewModel=viewModel()
    val chatWebSocket=ChatWebSocket(loadingViewModel)

    NavHost(navController = navController, startDestination = "login_page"){
        composable("login_page", content = { LoginPage(navController = navController,loadingViewModel, sharedPreferences) })
        composable("register_page", content = { RegisterPage(navController = navController,loadingViewModel) })
        composable("message_list", content = { MessageList(navController = navController, username =loadingViewModel.username.value, secret = loadingViewModel.secret.value,loadingViewModel,chatWebSocket) })
        composable("chat_history", content = { ChatHistory(navController = navController, username =loadingViewModel.username.value, secret = loadingViewModel.secret.value,loadingViewModel, sharedPreferences) })
        composable("no_chats", content = { NoChatsScreen(navController = navController,username =loadingViewModel.username.value,secret = loadingViewModel.secret.value, loadingViewModel, sharedPreferences) })
        composable("support", content = { Support(navController,username =loadingViewModel.username.value,secret = loadingViewModel.secret.value, loadingViewModel) })
    }
}