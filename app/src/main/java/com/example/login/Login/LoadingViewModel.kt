package com.example.login.Login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.Retrofit.CreateClass
import com.example.login.Retrofit.LiveChats.GetChatClass
import com.example.login.Retrofit.LiveChats.Typing
//import com.example.login.Retrofit.LiveChats.TypingClass
import com.example.login.Retrofit.LoginRetrofit.*
//import com.example.login.Retrofit.LoginClass
//import com.example.login.Retrofit.LoginRetrofit.LoginClass
import com.example.login.Retrofit.RetrofitAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadingViewModel: ViewModel() {
    private val _messageList = MutableStateFlow(emptyList<RecieveDataClass>())
    val messageList: StateFlow<List<RecieveDataClass>> = _messageList

    fun updateMessageList(newList: List<RecieveDataClass>) {
        _messageList.value = newList
    }
    var open = MutableStateFlow(false)
    var username = mutableStateOf("")
    val fnameValue =  mutableStateOf("")
    val lnameValue =  mutableStateOf("")
    var secret = mutableStateOf("")
    val confirmPasswordValue =  mutableStateOf("")
    val passwordVisibility = mutableStateOf(false)
    val confirmPasswordVisibility = mutableStateOf(false)

    val titleValue =  mutableStateOf("")

    val chatTitle = mutableStateOf("Untitled")

//    here it is used to validate login
    val headers = HashMap<String, String>()



    val hasRoom = mutableStateOf(false)

    var chatId = mutableStateOf(-1)

    val accessKey = mutableStateOf("")

    fun SignUp(): RetrofitAPI {
        val apiService = CreateClass(username, secret = secret).getInstance()
        return apiService
    }

    fun Authenticate(): RetrofitAPI {
        val apiService = LoginClass(username, secret = secret).getInstance()
        return apiService
    }

    var mylist:MutableList<GetMessagesDataClass> by mutableStateOf(mutableListOf())

    var mulChatsList:MutableList<GetAllChats> by mutableStateOf(mutableListOf())

    fun Chatss(): RetrofitAPI{
        val apiService = GetChatClass(username, secret = secret, chatId).getInstance()
        return apiService
    }


    val istyping = mutableStateOf(false)
    val istypinguser= mutableStateOf("")

    fun starttyping(){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                delay(2000L)
            }
            istyping.value=false
        }
    }



    fun startThread() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // Do the background work here
                // I'm adding delay
                delay(3000)
            }
            closeDialog()
        }
    }


    fun closeDialog() {
        open.value = false
    }
}