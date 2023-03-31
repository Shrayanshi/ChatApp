package com.example.login.Retrofit

import androidx.compose.runtime.MutableState
import com.example.login.Retrofit.LoginRetrofit.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT

interface RetrofitAPI {
    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("users")
    fun  // on below line we are creating a method to post our data.
            postData(
        @HeaderMap headers: Map<String, String>,
        @Body dataModel: DataModel?): Call<DataModel?>?

    @GET("users/me/")
    fun getCredentials(
        @HeaderMap headers: Map<String, String>): Call<ListModal>

    @PUT("chats/")
    fun CreateChat(@HeaderMap headers: HashMap<String, String>, @Body chats: Chats?): Call<Chats?>?

    @POST("messages/")
    fun getTexts(
        @HeaderMap headers: Map<String, String>, @Body text: Texts?): Call<Texts?>?

    @GET("messages/")
    fun getChats(
        @HeaderMap headers: Map<String, String>): Call<List<GetMessagesDataClass>>

    @GET("chats/")
    fun getAll(
        @HeaderMap headers: Map<String, String>): Call<List<GetAllChats>>

    @POST("typing/")
    fun getTyping(
        @HeaderMap headers: Map<String, String>, @Body type: Type?): Call<Type?>?
}


