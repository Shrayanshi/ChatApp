package com.example.login.Chats

import android.content.SharedPreferences
import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.CardDefaults
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LiveChats.CreateRoom
import com.example.login.ui.theme.Purple500
import com.example.login.ui.theme.Teal200

@Composable
fun NoChatsScreen(navController: NavController, username: String,secret: String, viewModel: LoadingViewModel, sharedPreferences: SharedPreferences) {
    val hasRoom = viewModel.hasRoom
    val ctx = LocalContext.current
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    Column() {
        TopAppBar(
            title = { Text(text = "Hi $username") },
            backgroundColor = Purple500,
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
//        Card(
//            //shape = MaterialTheme.shapes.medium,
//            shape = RoundedCornerShape(8.dp),
//            // modifier = modifier.size(280.dp, 240.dp)
//            modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
//            //set card elevation of the card
//            elevation =  10.dp,
//            contentColor = Color.Blue
//        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight(0.7f)) {
                Image(
                    painter = painterResource(id = com.example.login.R.drawable.no_display),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = " You Have No Chats",
                        fontSize = 35.sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold
                        //maxLines = 1,
                        //overflow = TextOverflow.Ellipsis,
//                        style = MaterialTheme.typography.titleSmall,
                    )
                }
//            }
        }
    }
    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = {
                navController.navigate("support")

//                CreateRoom(ctx, navController, "new", username, secret,viewModel)
//                navController.navigate("message_list")
                viewModel.hasRoom.value = true
            }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
    }
}