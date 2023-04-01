package com.example.login.Login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.login.Retrofit.LoginRetrofit.LoginUser
import com.example.login.ui.theme.primaryColor
import com.example.login.ui.theme.whiteBackground

@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginPage(navController: NavController,viewModel: LoadingViewModel, sharedPreferences: SharedPreferences) {

//    val viewModel: LoadingViewModel = viewModel()


    val openLoader by viewModel.open.collectAsState(initial = false)

    val context= LocalContext.current
    val image = painterResource(id = com.example.login.R.drawable.login_image)

    val usernameValue = viewModel.username
    val passwordValue = viewModel.secret

    val result = mutableStateOf("")

//    val usernameValue = remember { mutableStateOf("") }
//    val passwordValue = remember { mutableStateOf("") }
//    val passwordVisibility = remember { mutableStateOf(false) }

    val passwordVisibility = viewModel.passwordVisibility
    val focusRequester = remember { FocusRequester() }
    val username = sharedPreferences.getString("USERNAME", "").toString()
    val secret = sharedPreferences.getString("SECRET", "").toString()

    println("******************* $username")

    if (username.isNotBlank()){
        viewModel.username.value = username
        viewModel.secret.value = secret
        LoginUser(context,navController,username, secret, viewModel, result, sharedPreferences )
    }
    else{

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Image(painter = image, contentDescription = "login_image", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .verticalScroll(rememberScrollState())
//                .clip(RoundedCornerShape(topLeft = 30.dp, topRight = 30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {

                Text( text = "Sign In",
                    style = TextStyle(
                        fontSize=30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = usernameValue.value,
                        onValueChange = { usernameValue.value = it },
                        label = { Text(text = "Username") },
                        placeholder = { Text(text = "Username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                    )
                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                                Icon(painter = painterResource(id = com.example.login.R.drawable.password_eye), contentDescription = "pass",
                                    tint = if (passwordVisibility.value) primaryColor else Color.Red)
                            }
                        },
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester = focusRequester),
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            LoginUser(context,navController,usernameValue.value, passwordValue.value, viewModel, result, sharedPreferences )
                            viewModel.open.value = true
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "SIGN IN", fontWeight = Bold, fontSize = 18.sp)
                    }
                    if (openLoader) {
                        viewModel.startThread()
                        DialogBoxLoading()
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Create An Account",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("register_page"){
//                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }


            }
    }}
}

