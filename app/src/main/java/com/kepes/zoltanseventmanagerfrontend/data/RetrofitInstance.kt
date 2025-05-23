package com.kepes.zoltanseventmanagerfrontend.data

import com.kepes.zoltanseventmanagerfrontend.BuildConfig
import com.kepes.zoltanseventmanagerfrontend.service.UserApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

object RetrofitInstance {
    private val baseUrlBackend = BuildConfig.BASE_URL_BACKEND

    val api: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrlBackend)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }
}