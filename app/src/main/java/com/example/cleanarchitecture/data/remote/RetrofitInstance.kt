package com.example.cleanarchitecture.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://randomuser.me/").addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiService() : APIService {
        return getInstance().create(APIService::class.java)
    }
}