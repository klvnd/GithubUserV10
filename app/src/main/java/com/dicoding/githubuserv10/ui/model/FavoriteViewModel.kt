package com.dicoding.githubuserv10.ui.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubuserv10.data.database.FavoriteUser
import com.dicoding.githubuserv10.data.database.FavoriteUserDao
import com.dicoding.githubuserv10.data.database.FavoriteUserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserDatabase?

    init {
        userDb = FavoriteUserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}