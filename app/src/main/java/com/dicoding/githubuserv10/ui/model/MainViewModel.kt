package com.dicoding.githubuserv10.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserv10.data.api.ApiConfig
import com.dicoding.githubuserv10.data.response.User
import com.dicoding.githubuserv10.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        ApiConfig.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getData() {
        ApiConfig.apiInstance
            .getData()
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        listUser.postValue(ArrayList(response.body()))
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.d("Failed to get data", t.message.toString())
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUser
    }
}