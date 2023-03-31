package com.example.login.Retrofit.LiveChats

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.login.Login.DialogBoxLoading
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LoginRetrofit.GetAllChats
import com.example.login.Retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun GetAllChats(ctx: Context, navController: NavController, username: String, secret: String, viewModel: LoadingViewModel){
    var url = "https://api.chatengine.io/"

    val headers = HashMap<String, String>()
    headers["Project-ID"] = "e11eff09-4d56-45bc-99ba-daf1503b5ae0"
    headers["User-Name"] = "$username"
    headers["User-Secret"] = "$secret"

    val retrofit = Retrofit.Builder()
        .baseUrl(url)

        .addConverterFactory(GsonConverterFactory.create())

        .build()

    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val call: Call<List<GetAllChats>> = retrofitAPI.getAll(headers)

    call!!.enqueue(object : Callback<List<GetAllChats>?> {
        override fun onResponse(call: Call<List<GetAllChats>?>?, response: Response<List<GetAllChats>?>) {

            val model2: List<GetAllChats>? = response.body()
            viewModel.mulChatsList= model2 as MutableList<GetAllChats>

            if(response.isSuccessful && viewModel.username.value !== ""){
                viewModel.open.value == true
//                Toast.makeText(ctx, "Got all the chats for you", Toast.LENGTH_SHORT).show()
                if(viewModel.mulChatsList.size == 0){
//                    println("//////////////////////////////////////${viewModel.mulChatsList.size}")
                    navController.navigate("no_chats")

                }else{
                    navController.navigate("chat_history"){
//                                popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                }
            }
//


            println("############################### ${viewModel.mulChatsList}")

//            result.value = resp
        }

        override fun onFailure(call: Call<List<GetAllChats>?>?, t: Throwable) {

            Toast.makeText(ctx, "Can't create a room right now!", Toast.LENGTH_SHORT)
                .show()
        }
    })
}