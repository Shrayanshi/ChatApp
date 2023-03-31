package com.example.login.Retrofit

import androidx.compose.runtime.MutableState
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateClass(username: MutableState<String>, secret: MutableState<String>){

    val username=username
    val secret=secret
    var url = "https://api.chatengine.io/"

    fun getInstance(): RetrofitAPI {
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