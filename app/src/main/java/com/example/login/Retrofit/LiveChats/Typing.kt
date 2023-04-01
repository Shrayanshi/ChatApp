package com.example.login.Retrofit.LiveChats

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.login.Retrofit.LoginRetrofit.Texts
import com.example.login.Retrofit.LoginRetrofit.Type
import com.example.login.Retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Typing(ctx: Context, navController: NavController, username: String, secret: String, chatId: MutableState<Int>){
    val x=chatId.value
    var url = "https://api.chatengine.io/chats/$x/"
    // on below line we are creating a retrofit
    // builder and passing our base url

    val headers = HashMap<String, String>()
    headers["Project-ID"] = "4eb4eb0a-47cf-4c45-8814-9af4029f3924"
    headers["User-Name"] = "$username"
    headers["User-Secret"] = "$secret"

    println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+x)


    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        // as we are sending data in json format so
        // we have to add Gson converter factory
//        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        // at last we are building our retrofit builder.
        .build()


    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val type = Type(0,"")

    val call: Call<Type?>? = retrofitAPI.getTyping(headers, type)

    call!!.enqueue(object : Callback<Type?> {
        override fun onResponse(call: Call<Type?>?, response: Response<Type?>) {

//            Toast.makeText(ctx, "$response", Toast.LENGTH_SHORT).show()
            println("@@@@@@@@@@ $response")
            navController.navigate("message_list"){
//                                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }

            val model: Type? = response.body()

            val resp = model?.id

//            result.value = resp.toString()

//            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ $chatId")
        }

        override fun onFailure(call: Call<Type?>?, t: Throwable) {

            Toast.makeText(ctx, "Login failed! Incorrect Details", Toast.LENGTH_SHORT)
                .show()
        }
    })
}