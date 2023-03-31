package com.example.login.Room

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.login.Login.DialogBoxLoading
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LiveChats.CreateRoom
import com.example.login.ui.theme.Purple500
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Support(navController: NavController,username:String, secret:String, viewModel: LoadingViewModel){
    val ctx = LocalContext.current
    val questionViewModel:QuestionViewModel= viewModel()
    val openLoader by viewModel.open.collectAsState(initial = false)
    LaunchedEffect(true){
        withContext(Dispatchers.IO){
            QuestionListing(questionViewModel)
        }
    }
    Column() {
        TopAppBar(
            title = {
                    Text(text = "Start a Chat", fontSize = 20.sp)
                },
            navigationIcon = {
                IconButton(onClick = {navController.navigate("chat_history")}) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            }, modifier = Modifier.statusBarsPadding()
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)) {
            QuestionList(questionViewModel, viewModel)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
                .padding(bottom = 5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple500),
            onClick = {
                CreateRoom(ctx, navController, "${viewModel.chatTitle.value}",username,secret, viewModel)
            }) {
            Text(text = "Support", fontSize = 20.sp)
        }
    }
    if (openLoader) {
        viewModel.startThread()
        DialogBoxLoading()
    }
}
