package com.example.login.Retrofit.LoginRetrofit

import androidx.compose.runtime.MutableState

data class DataModel(
    var username: String,
    var first_name: String,
    var last_name: String,
    var secret: String
)

data class ListModal(
    val username: String,
    val secret: String
)

data class Chats(
    val usernames: List<String>,
    val title: String,
    val id: Int
)

data class Texts(
    val text: String
)

data class GetMessagesDataClass(
    val sender_username:String,
    val text:String,
    val created: String,
//    val id: Int
    )

data class RecieveDataClass(
    var text:String,
    var created:String,
    var sender_username:String
)

data class person(
    val username: String
)
data class GetAllChats(
    val title: String,
    val created: String,
    val id: Int,
    val access_key: String,
    val people: List<person>
)

data class Type(
    val id: Int,
    val person: String
)