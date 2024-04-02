package com.dicoding.githubuserv10.data.api

import com.dicoding.githubuserv10.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", BuildConfig.KEY)
            .build()
        chain.proceed(requestHeaders)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiInstance = retrofit.create(ApiService::class.java)
}