package com.dicoding.githubuserv10.data.response

data class User(
    val login: String,
    val id: Int,
    val name: String? = null,
    val avatar_url: String? = null
)
