package com.example.login.Retrofit.LiveChats

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LoginRetrofit.DataModel
import com.example.login.Retrofit.LoginRetrofit.GetMessagesDataClass
import com.example.login.Retrofit.LoginRetrofit.ListModal
import com.example.login.Retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun GetChats(ctx: Context, navController: NavController, username:String, secret:String, viewModel: LoadingViewModel, result: MutableState<String>) {

    viewModel.headers["Project-ID"] = "4eb4eb0a-47cf-4c45-8814-9af4029f3924"
    viewModel.headers["User-Name"] = "$username"
    viewModel.headers["User-Secret"] = "$secret"

    val retrofitAPI = viewModel.Chatss()
    val call: Call<List<GetMessagesDataClass>> = retrofitAPI.getChats(viewModel.headers)
    call!!.enqueue(object : Callback<List<GetMessagesDataClass>?> {
        override fun onResponse(
            call: Call<List<GetMessagesDataClass>?>,
            response: Response<List<GetMessagesDataClass>?>
        ) {
            val model: List<GetMessagesDataClass>? = response.body()
//            val resp = model!!.size
//            result.value = resp.toString()

            println("this is in get chat @@@@@@@@@@@@@@@@@@@@@@@@@${result.value}")
//            if (response.isSuccessful) {
//                navController.navigate("message_list") {
////                                popUpTo = navController.graph.startDestination
//                    launchSingleTop = true
//                }
//            }
            viewModel.open.value = false

            val model1: List<GetMessagesDataClass>? = response.body()
            viewModel.mylist= model1 as MutableList<GetMessagesDataClass>
            // on below line we are getting our data from model class
            // and adding it to our string.
            val resp1 =
                "Response Code : " + response.code() + "\n" + "User Name : " + model1.size + "\n" + "Job : " + model1.size
            // below line we are setting our string to our response.
            result.value = resp1
            println("////////////////////////////////////////////////// size:$resp1")
        }

        override fun onFailure(call: Call<List<GetMessagesDataClass>?>, t: Throwable) {
            // displaying an error message in toast
            result.value = "Error found is : " + t.message
        }
    })
}

class GetChatClass(username: MutableState<String>, secret: MutableState<String>, chatId: MutableState<Int>){

    val username=username
    val secret=secret
    val x=chatId.value
    var url = "https://api.chatengine.io/chats/$x/"

    fun getInstance(): RetrofitAPI {
        println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$ $x")
        val headers = HashMap<String, String>()
        headers["Project-ID"] = "e11eff09-4d56-45bc-99ba-daf1503b5ae0"
        headers["User-Name"] = "$username"
        headers["User-Secret"] = "$secret"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            // on below line we are calling add
            // Converter factory as Gson converter factory.
            .addConverterFactory(GsonConverterFactory.create())
            // at last we are building our retrofit builder.
            .build().create(RetrofitAPI::class.java)
        return retrofit!!
    }

}