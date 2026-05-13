package com.example.cleanarchitecture.data.remote

import com.example.cleanarchitecture.data.model.RandomResponse
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("api/")
    suspend fun getUser(
        @Query("results") result : Int = 20
    ) : RandomResponse
}