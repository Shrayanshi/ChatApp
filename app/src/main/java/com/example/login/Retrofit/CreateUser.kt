package com.example.login.Retrofit

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.login.Login.LoadingViewModel
import com.example.login.Retrofit.LoginRetrofit.DataModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun CreateUser(ctx: Context,navController: NavController, userName: MutableState<String>, fname:MutableState<String>, lname:MutableState<String>, password:MutableState<String>, result: MutableState<String>, viewModel: LoadingViewModel): Unit {

//    val headers = HashMap<String, String>()
    viewModel.headers["PRIVATE-KEY"] = "1ebaa208-dcda-4be2-abad-e673ae2223fe"

    val retrofitAPI = viewModel.SignUp()

    val dataModel = DataModel(userName.value, fname.value, lname.value, password.value)

    val call: Call<DataModel?>? = retrofitAPI.postData(viewModel.headers, dataModel)

    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {

            Toast.makeText(ctx, "Successfully Signed Up! Login now!", Toast.LENGTH_SHORT).show()
            navController.navigate("login_page"){
//                                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }

            val model: DataModel? = response.body()

            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model?.username + "\n" + "Job : " + model?.first_name

            result.value = resp
        }

        override fun onFailure(call: Call<DataModel?>?, t: Throwable) {

            result.value = "Error found is : " + t.message
        }
    })
}