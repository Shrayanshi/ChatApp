package com.example.login.Retrofit.LiveChats

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.login.Retrofit.LoginRetrofit.Texts
import com.example.login.Retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun ChatList(ctx: Context, navController: NavController, title: MutableState<String>, username: String, secret: String, result: MutableState<String>, chatId: MutableState<Int>){
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

    val text = Texts(title.value)

    val call: Call<Texts?>? = retrofitAPI.getTexts(headers, text)

    call!!.enqueue(object : Callback<Texts?> {
        override fun onResponse(call: Call<Texts?>?, response: Response<Texts?>) {

//            Toast.makeText(ctx, "$response", Toast.LENGTH_SHORT).show()
            println("@@@@@@@@@@ $response")
            navController.navigate("message_list"){
//                                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }

            val model: Texts? = response.body()

            val resp = model!!.text

            result.value = resp

//            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ $chatId")
        }

        override fun onFailure(call: Call<Texts?>?, t: Throwable) {

            Toast.makeText(ctx, "Login failed! Incorrect Details", Toast.LENGTH_SHORT)
                .show()
        }
    })
}