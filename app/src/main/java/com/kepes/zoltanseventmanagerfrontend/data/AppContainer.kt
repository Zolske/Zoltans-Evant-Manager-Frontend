package com.kepes.zoltanseventmanagerfrontend.data

import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kepes.zoltanseventmanagerfrontend.BuildConfig
import com.kepes.zoltanseventmanagerfrontend.service.UserApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val userRepository: UserRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrlBackend = BuildConfig.BASE_URL_BACKEND

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlBackend)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    /**
     * DI implementation for User repository
     */
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(retrofitService)
    }
}