package com.example.login.Chats

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.inputmethodservice.Keyboard.Row
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login.Login.DialogBoxLoading
import com.example.login.Login.LoadingViewModel
import com.example.login.Login.ProgressIndicator
import com.example.login.MainActivity
import com.example.login.R
import com.example.login.Retrofit.LiveChats.CreateRoom
import com.example.login.Retrofit.LiveChats.GetAllChats
import com.example.login.Retrofit.LiveChats.GetChats
import com.example.login.Room.QuestionList
import com.example.login.Room.QuestionViewModel
import com.example.login.ui.theme.Purple500
import com.example.login.ui.theme.Purple700
import com.example.login.ui.theme.Teal200
import com.example.login.ui.theme.primaryColor


@SuppressLint("UnrememberedMutableState")
@Composable
fun ChatHistory(navController: NavController, username: String, secret: String,viewModel: LoadingViewModel, sharedPreferences: SharedPreferences){
    val result = mutableStateOf("")
    val ctx = LocalContext.current
    val hasRoom = viewModel.hasRoom
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val openLoader by viewModel.open.collectAsState(initial = false)


//    if (openLoader) {
//        viewModel.startThread()
//        DialogBoxLoading()
//    }
    Column() {
        TopAppBar(
            title = { Text(text = "Hi $username") },
            actions = {
                IconButton(onClick = {
                    editor.putString("USERNAME", "")
                    editor.putString("SECRET", "")

                    // on the below line we are applying
                    // changes to our shared prefs.
                    editor.apply()
                    navController.navigate("login_page"){
                        popUpTo("login_page")
                    }
                }) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "")
                }
            }
            , modifier = Modifier.statusBarsPadding()
        )
//        {
//            Text(
//                text = "Log Out",
//                modifier = Modifier.clickable(onClick = {
//                    editor.putString("USERNAME", "")
//                    editor.putString("SECRET", "")
//
//                    // on the below line we are applying
//                    // changes to our shared prefs.
//                    editor.apply()
//                    navController.navigate("login_page"){
//                        popUpTo("login_page")
//                    }
////                    val intent = Intent(ctx, MainActivity::class.java)
////                    intent.flags =
////                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                    ctx.startActivity(intent)
//                })
//            )
//        }
        Text(text = "Your Chats", fontSize = 18.sp, color = Color.LightGray, fontStyle = FontStyle.Italic, modifier = Modifier.padding(start = 10.dp))
        LazyColumn(modifier = Modifier.fillMaxHeight(0.95f)){
            itemsIndexed(viewModel.mulChatsList){
                index, item ->

                Card(
                    //shape = MaterialTheme.shapes.medium,
                    shape = RoundedCornerShape(8.dp),
                    // modifier = modifier.size(280.dp, 240.dp)
                    modifier = Modifier
                        .padding(10.dp, 5.dp, 10.dp, 10.dp)
                        .clickable(onClick = {
                            viewModel.chatId.value = item.id
                            viewModel.accessKey.value = item.access_key
                            viewModel.mylist = mutableListOf()
                            navController.navigate("message_list")
                            viewModel.open.value = true
                            GetChats(ctx, navController, username, secret, viewModel, result)
                        }
                        ),
                    //set card elevation of the card
                    elevation =  10.dp,
                    contentColor = Color.Blue
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.pic),
                            contentDescription = null, // decorative
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Title: ${item.title}",
                                fontSize = 22.sp,
//                        style = MaterialTheme.typography.titleMedium,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
//                            if(username != "Admin") {
//                                Text(text = "Chat with User", fontSize = 18.sp)
//                                Text(text = "Chat with Admin", fontSize = 18.sp)
//                            }
                                    Text(
                                        text = "Id: ${item.id}",
                                        fontSize = 16.sp,
                                        color = Color.DarkGray
                                        //maxLines = 1,
                                        //overflow = TextOverflow.Ellipsis,
//                        style = MaterialTheme.typography.titleSmall,
                                    )
                        }
                    }
                }
            }
        }
//        Button(
//            onClick = {
////                navController.navigate("message_list")
////                GetChats(ctx, navController, username, secret, viewModel, result)
//            },
//            modifier = Modifier
//                .padding(15.dp)
//                .fillMaxWidth(0.8f)
//                .height(50.dp)
//        ) {
//            Text(text = "Show chat")
//        }
    }
    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = {
                navController.navigate("support")
                viewModel.open.value = true
//                    Toast.makeText(ctx, "Your already have a room!", Toast.LENGTH_SHORT).show()
//                    navController.navigate("message_list")
            }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
    }

}