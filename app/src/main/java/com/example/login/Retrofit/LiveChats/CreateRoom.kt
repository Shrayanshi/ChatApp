package com.example.login.Retrofit.LiveChats

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LoginRetrofit.Chats
import com.example.login.Retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun CreateRoom(
    ctx: Context, navController: NavController,
    title: String, username: String, secret: String,
    viewModel: LoadingViewModel
) {

    var url = "https://api.chatengine.io/"
    val result = mutableStateOf("")

    val headers = HashMap<String, String>()
    headers["Project-ID"] = "4eb4eb0a-47cf-4c45-8814-9af4029f3924"
    headers["User-Name"] = "$username"
    headers["User-Secret"] = "$secret"


    val retrofit = Retrofit.Builder()
        .baseUrl(url)

        .addConverterFactory(GsonConverterFactory.create())

        .build()

    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val chats = Chats(listOf("Admin"),title,0)

    val call: Call<Chats?>? = retrofitAPI.CreateChat(headers, chats)

    call!!.enqueue(object : Callback<Chats?> {
        override fun onResponse(call: Call<Chats?>?, response: Response<Chats?>) {
            Toast.makeText(ctx, "Successfully created a room", Toast.LENGTH_SHORT).show()
//            navController.navigate("message_list"){
////                                popUpTo = navController.graph.startDestination
//                launchSingleTop = true
//            }
            GetAllChats(ctx, navController, username, secret, viewModel)

            val model: Chats? = response.body()
            if (model != null) {
                viewModel.chatId.value=model.id
            }

        }

        override fun onFailure(call: Call<Chats?>?, t: Throwable) {

            Toast.makeText(ctx, "Can't create a room right now!", Toast.LENGTH_SHORT)
                .show()
        }
    })
}