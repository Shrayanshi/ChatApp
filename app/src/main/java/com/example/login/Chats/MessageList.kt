package com.example.login.Chats

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.login.ChatWebSocket
import com.example.login.Login.DialogBoxLoading
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LiveChats.ChatList
import com.example.login.Retrofit.LiveChats.Typing
import com.example.login.ui.theme.Purple500
import com.example.login.ui.theme.whiteBackground

@SuppressLint("UnrememberedMutableState", "StateFlowValueCalledInComposition")
@Composable
fun MessageList(navController: NavController, username: String, secret: String,viewModel: LoadingViewModel,chatWebSocket: ChatWebSocket){
//    val viewModel: LoadingViewModel = viewModel()
    val result = mutableStateOf("")
    val titleValue = viewModel.titleValue
    val ctx = LocalContext.current
    val isVisible by remember {
        derivedStateOf {
            titleValue.value.isNotBlank()
        }
    }

    val cornerRadius = 50.dp
    val gradientColors = listOf(Color(0xFF7b4397), Color(0xFFCCCCFF))
    val gradientColors2 = listOf(Color(0xFF7b4397), Color.DarkGray)

    val messageListState = viewModel.messageList.collectAsState()
    val messageList = messageListState.value
    val openLoader by viewModel.open.collectAsState(initial = false)
    Column() {
        TopAppBar(
            title = {
                Column() {
                    if (username == "Admin") {
                        Text(text = "User")
                    } else {
                        Text(text = "Admin")
                    }
                    if (viewModel.istyping.value && viewModel.username.value != viewModel.istypinguser.value) {
                        Column() {
                            Text(text = "typing...", color = LightGray, fontSize = 15.sp)
                        }
                        viewModel.starttyping()
//                    isTYping=false
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("chat_history") }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            actions = {
                Image(
                    painter = painterResource(id = com.example.login.R.drawable.pic),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            },
            modifier = Modifier.statusBarsPadding()
        )
//        {
//            Text(text = username ,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center,
//                color = Color.White)
//        }
//        Text(text = messageList.size.toString())
//        if(viewModel.mylist)

        if (viewModel.mylist.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier
                        .background(whiteBackground),
//                                .fillMaxWidth()
//                                .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    content = {
                        Column(modifier = Modifier.background(Color.DarkGray)) {
                            Text(
                                text = "Welcome to the chat!",
                                color = Color.White,
                                modifier = Modifier.padding(10.dp),
                                fontSize = 20.sp
                            )
//                                    Text(text = item.sender_username, fontSize = 15.sp, modifier = Modifier.padding(start = 10.dp, bottom = 10.dp))
                        }
                    }
                )

            }
        }
        LazyColumn(modifier = Modifier.fillMaxHeight(0.78f), reverseLayout = true) {
            itemsIndexed(viewModel.mylist.sortedByDescending { it.created }) { index, item ->
                if (item.sender_username == username) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Card(
                            modifier = Modifier
                                .background(whiteBackground),
//                                .fillMaxWidth()
//                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            content = {
                                Column(modifier = Modifier.background(Color(0xFFCCCCFF))) {
                                    Row() {
                                        Text(
                                            item.text,
                                            modifier = Modifier.padding(10.dp),
                                            fontSize = 20.sp
                                        )
                                        Text(
                                            text = item.created.substring(10, 16),
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
//                                    Text(text = item.sender_username, fontSize = 15.sp, modifier = Modifier.padding(start = 10.dp, bottom = 10.dp))
                                }
                            }
                        )

                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            modifier = Modifier
                                .background(whiteBackground),
//                                .fillMaxWidth()
//                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            content = {
                                Column(modifier = Modifier.background(Color.DarkGray)) {
                                    Row() {
                                        Text(
                                            text = item.text,
                                            color = Color.White,
                                            modifier = Modifier.padding(10.dp),
                                            fontSize = 20.sp
                                        )
                                        Text(
                                            text = item.created.substring(10, 16),
                                            color = Color.White,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
//                                    Text(text = item.sender_username, fontSize = 15.sp, modifier = Modifier.padding(start = 10.dp, bottom = 10.dp))
                                }
                            }
                        )

                    }
                }
            }
        }
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
                    .navigationBarsPadding()
            ) {
                OutlinedTextField(
                    value = titleValue.value,
                    onValueChange = {
                        Typing(ctx, navController, username, secret, viewModel.chatId)
                        titleValue.value = it
                    },
//                label = { Text(text = "Text") },
                    placeholder = { Text(text = "Message...") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Purple500,
                        unfocusedBorderColor = DarkGray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    trailingIcon = {
                        if (isVisible) {
                            IconButton(
                                onClick = { titleValue.value = "" }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    },
                    modifier = Modifier
//                        .heightIn(min = 50.dp)
                        .fillMaxWidth(0.8f),
                )
                Spacer(modifier = Modifier.padding(end = 5.dp))
                if (isVisible) {
                    Button(
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
//                    .padding(start = 32.dp, end = 32.dp)
                        , onClick = {
                            if (isVisible) {

                                chatWebSocket.sendMessage(titleValue.value)
                                ChatList(
                                    ctx,
                                    navController,
                                    titleValue,
                                    username,
                                    secret,
                                    result,
                                    viewModel.chatId
                                )
                            }
                            titleValue.value = ""
                        },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(cornerRadius)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.linearGradient(colors = gradientColors),
                                    shape = RoundedCornerShape(cornerRadius)
                                )
                                .padding(horizontal = 4.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "send",
                                tint = Color.White,

                                )
//                    Text(
//                        text = "Send",
//                        fontSize = 20.sp,
//                        color = Color.White
//                    )
                        }
                    }
                } else {
                    Button(
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
//                    .padding(start = 32.dp, end = 32.dp)
                        , onClick = {
                        },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(cornerRadius)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(cornerRadius)
                                )
                                .padding(horizontal = 4.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "send",
                                tint = Color.White,

                                )
//                    Text(
//                        text = "Send",
//                        fontSize = 20.sp,
//                        color = Color.White
//                    )
                        }

                    }
                }

//            Button(
//                onClick = {
//                    chatWebSocket.sendMessage(titleValue.value)
//                ChatList(ctx, navController,titleValue, username, secret, result, viewModel.chatId)
//                    titleValue.value=""
////                    Toast.makeText(ctx, "$username", Toast.LENGTH_SHORT).show()
////                    GetChats(ctx, navController, username, secret, viewModel, result)
//                },
//                modifier = Modifier
//                    .fillMaxWidth(1f)
//                    .height(50.dp)
//            ) {
//                Text(text = "Send")
//            }
            }
    }

//        Text(text = result.value)
    if (openLoader) {
        viewModel.startThread()
        DialogBoxLoading()
    }
}
