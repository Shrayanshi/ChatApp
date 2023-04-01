package com.example.login.Retrofit.LoginRetrofit

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LiveChats.GetAllChats
//import com.example.login.Retrofit.DataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun LoginUser(
    ctx: Context,
    navController: NavController, username: String, secret: String, viewModel: LoadingViewModel, result: MutableState<String>, sharedPreferences: SharedPreferences){

    viewModel.headers["Project-ID"] = "4eb4eb0a-47cf-4c45-8814-9af4029f3924"
    viewModel.headers["User-Name"] = "$username"
    viewModel.headers["User-Secret"] = "$secret"

    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    val retrofitAPI = viewModel.Authenticate()
//    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)


    val call: Call<ListModal> = retrofitAPI.getCredentials(viewModel.headers)

    call!!.enqueue(object : Callback<ListModal?> {
        override fun onResponse(
            call: Call<ListModal?>,
            response: Response<ListModal?>
        ) {

//            Toast.makeText(ctx,"Logged In Successfully!", Toast.LENGTH_SHORT).show()
            val model: ListModal? = response.body()
            // on below line we are getting our data from model class
            // and adding it to our string.
            val resp = model?.username
            if (resp != null) {
                result.value = resp
            }

            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@${result.value}")

            if(response.isSuccessful){
                GetAllChats(ctx, navController, username, secret, viewModel)
//                navController.navigate("chat_history"){
////                                popUpTo = navController.graph.startDestination
//                    launchSingleTop = true
//                }
                editor.putString("USERNAME", username.toString())
                editor.putString("SECRET", secret.toString())
                editor.apply()

            }else if(username == "" || secret == ""){
                Toast.makeText(ctx,"Please fill all the details", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(ctx, "Please fill the correct details", Toast.LENGTH_SHORT).show()
            }

        }

        override fun onFailure(call: Call<ListModal?>, t: Throwable) {
            // displaying an error message in toast
            Toast.makeText(ctx, "Login failed!", Toast.LENGTH_SHORT)
                .show()
        }
    })
}