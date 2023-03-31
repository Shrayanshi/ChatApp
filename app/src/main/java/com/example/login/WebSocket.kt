package com.example.login

import android.util.Log
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LoginRetrofit.GetMessagesDataClass
import com.example.login.Retrofit.LoginRetrofit.RecieveDataClass
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.net.SocketException

open class ChatWebSocket(private val loadingViewModel: LoadingViewModel): WebSocketListener(){
    var webSocket: WebSocket
    val x = loadingViewModel.chatId.value
    val access = loadingViewModel.accessKey.value

    init {
        val request = Request.Builder().url("wss://api.chatengine.io/chat/?projectID=e11eff09-4d56-45bc-99ba-daf1503b5ae0&chatID=${x}&accessKey=${access}").build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("--------------------- $x")
        Log.d("MYTAG", "WebSocket connection established.")
//        webSocket.send("Hello, server!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val gson = Gson()
        val json = JSONObject(text)
        val action = json.getString("action")

        if(action=="is_typing"){
            val data=json.getJSONObject("data")
            val name=data.getString("person")
            loadingViewModel.istyping.value=true
            loadingViewModel.istypinguser.value= name.toString()
        }
        if (action == "new_message") {
            val message = json.getJSONObject("data").getJSONObject("message")
            val receivedMessage = GetMessagesDataClass(
                text = message.getString("text"),
                created = message.getString("created"),
                sender_username = message.getString("sender_username")
            )
//            loadingViewModel.updateMessageList((loadingViewModel.messageList.value+message) as List<GetMessagesDataClass>)
            loadingViewModel.mylist.add(receivedMessage)
            loadingViewModel.updateMessageList((loadingViewModel.messageList.value + message) as List<RecieveDataClass>)
            Log.d("MYTAG", "onMessage: $receivedMessage ")
        }
    }

    fun onClose(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("MYTAG", "WebSocket connection closed.")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        if (t is SocketException && t.message?.contains("Broken pipe") == true) {
            Log.d("MYTAG", "WebSocket failure: Broken pipe")
            // Reconnect the WebSocket here
            val request = Request.Builder()
                .url("wss://api.chatengine.io/chat/?projectID=52690bdb-3b85-4b96-9081-27fa9b4dc10e&chatID=153464&accessKey=ca-529db72b-f253-4bdb-9be1-8719383ecc2a")
                .build()
            val client = OkHttpClient()
            this.webSocket = client.newWebSocket(request, this)
        }
        else{Log.d("MYTAG", "WebSocket failure.", t)}
        Log.d("MYTAG", "WebSocket failure.", t)

    }

    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    fun closeWebSocket() {
        webSocket.close(1000, "Closing WebSocket.")
    }
}