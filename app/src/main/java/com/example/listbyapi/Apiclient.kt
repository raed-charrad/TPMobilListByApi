package com.example.listbyapi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Apiclient {
    private const val BASE_URL: String = "http://serviceoffreiset.herokuapp.com/"
    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }
    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }
    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    val apiService :  ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}