package com.dicoding.githubuserv10.ui.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuserv10.data.api.ApiConfig
import com.dicoding.githubuserv10.data.database.FavoriteUser
import com.dicoding.githubuserv10.data.database.FavoriteUserDao
import com.dicoding.githubuserv10.data.database.FavoriteUserDatabase
import com.dicoding.githubuserv10.data.response.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailResponse>()

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserDatabase?

    init {
        userDb = FavoriteUserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setDetail(username: String) {
        ApiConfig.apiInstance.getUserDetail(username).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>, response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getDetail(): LiveData<DetailResponse> {
        return user
    }

    suspend fun addToFavorite(username: String?, id: Int, avatar_url: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = username?.let { FavoriteUser(it, id, avatar_url) }
            user?.let { userDao?.addToFavorite(it) }
        }
    }


    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}