package com.kepes.zoltanseventmanagerfrontend.service

import com.kepes.zoltanseventmanagerfrontend.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    @GET("user/{idToken}")
    suspend fun getUser(@Path("idToken")idToken: String): User

    @GET("greeting")
    suspend fun getGreeting(): String
}