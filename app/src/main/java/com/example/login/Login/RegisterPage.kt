package com.example.login.Login

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.login.R
import com.example.login.Retrofit.CreateUser
import com.example.login.ui.theme.primaryColor
import com.example.login.ui.theme.whiteBackground

@Composable
fun RegisterPage(navController: NavController,viewModel: LoadingViewModel) {

    val context= LocalContext.current
    val image = painterResource(id = R.drawable.register_page)

//    val viewModel: LoadingViewModel = viewModel()

    val usernameValue = viewModel.username
    val passwordValue = viewModel.secret
    val fnameValue = viewModel.fnameValue
    val lnameValue = viewModel.lnameValue
    val confirmPasswordValue = viewModel.confirmPasswordValue

//    val usernameValue = remember { mutableStateOf("") }
//    val fnameValue = remember { mutableStateOf("") }
//    val lnameValue = remember { mutableStateOf("") }
//    val passwordValue = remember { mutableStateOf("") }
//    val confirmPasswordValue = remember { mutableStateOf("") }


    val response = remember { mutableStateOf("") }

    val passwordVisibility = viewModel.passwordVisibility
    val confirmPasswordVisibility = viewModel.confirmPasswordVisibility
//    val passwordVisibility = remember { mutableStateOf(false) }
//    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(painter = image, contentDescription = "registerimage")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .verticalScroll(rememberScrollState())
//                .clip(RoundedCornerShape(topLeft = 30.dp, topRight = 30.dp))
                .background(whiteBackground)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
                Text(
                    text = "Sign Up", fontSize = 30.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = usernameValue.value,
                        onValueChange = { usernameValue.value = it },
                        label = { Text(text = "Username") },
                        placeholder = { Text(text = "Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    OutlinedTextField(
                        value = fnameValue.value,
                        onValueChange = { fnameValue.value = it },
                        label = { Text(text = "First name") },
                        placeholder = { Text(text = "First Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    OutlinedTextField(
                        value = lnameValue.value,
                        onValueChange = { lnameValue.value = it },
                        label = { Text(text = "Last name") },
                        placeholder = { Text(text = "Last Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.password_eye),
                                    contentDescription = "pass",
                                    tint = if (passwordVisibility.value){ primaryColor} else { Color.Gray}
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    )

                    OutlinedTextField(
                        value = confirmPasswordValue.value,
                        onValueChange = { confirmPasswordValue.value  = it },
                        label = { Text(text = "Confirm Password") },
                        placeholder = { Text(text = "Confirm Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.password_eye),
                                    contentDescription = "pass",
                                    tint = if (passwordVisibility.value){ primaryColor} else { Color.Red}
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {
                        if(registerUtils(usernameValue.value, fnameValue.value, lnameValue.value, passwordValue.value,
                                confirmPasswordValue.value, context) == 1){
                            CreateUser(context,navController, usernameValue, fnameValue, lnameValue.value, passwordValue.value, response, viewModel)
                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)) {
                        Text(text = "SIGN UP", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Login Instead",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("login_page"){
//                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))

                }
        }
    }
}

@Composable
fun DialogBoxLoading(
    cornerRadius: Dp = 16.dp,
    paddingStart: Dp = 56.dp,
    paddingEnd: Dp = 56.dp,
    paddingTop: Dp = 32.dp,
    paddingBottom: Dp = 32.dp,
    progressIndicatorColor: Color = Color(0xFF35898f),
    progressIndicatorSize: Dp = 80.dp
) {

    Dialog(
        onDismissRequest = {
        }
    ) {
        Surface(
            elevation = 4.dp,
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = paddingStart, end = paddingEnd, top = paddingTop),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ProgressIndicatorLoading(
                    progressIndicatorSize = progressIndicatorSize,
                    progressIndicatorColor = progressIndicatorColor
                )

                // Gap between progress indicator and text
                Spacer(modifier = Modifier.height(32.dp))

                // Please wait text
                Text(
                    modifier = Modifier
                        .padding(bottom = paddingBottom),
                    text = "Please wait...",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
            }
        }
    }
}

@Composable
fun ProgressIndicatorLoading(progressIndicatorSize: Dp, progressIndicatorColor: Color) {

    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
            }
        )
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                12.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White, // add background color first
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White // Set background color
    )
}

@Composable
fun ProgressIndicator(progressIndicatorSize: Dp, progressIndicatorColor: Color){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp),
            color = Color.Blue,
            strokeWidth = 5.dp,
        )
    }
}

fun registerUtils(username:String, fname:String, lname:String, password:String, cpassword:String, context: Context): Int{
    var ru = 0
    if (username.trim().isNotEmpty() && fname.trim().isNotEmpty() && lname.trim().isNotEmpty()){
            if (password.trim().isNotEmpty() && cpassword.trim().isNotEmpty()){
                    if(password == cpassword){
                        ru = 1
                    }else Toast.makeText(context,"Passwords should be matched!", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(context,"Please fill all the details!", Toast.LENGTH_SHORT).show()
    } else Toast.makeText(context,"Please fill all the details!", Toast.LENGTH_SHORT).show()
    return ru
}

//fun isValidPassword(password: String?) : Boolean {
//    password?.let {
//        val passwordPattern = "^(?=.[0-8])(?=.[a-z])(?=.[@#$%^&+=])"
//        val passwordMatcher = Regex(passwordPattern)
//
//        return passwordMatcher.find(password) != null
//    } ?: return true
//}