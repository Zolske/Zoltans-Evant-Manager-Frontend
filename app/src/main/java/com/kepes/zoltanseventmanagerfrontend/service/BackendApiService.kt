package com.kepes.zoltanseventmanagerfrontend.service

import com.kepes.zoltanseventmanagerfrontend.BuildConfig
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

// url to the backend server
private const val BASE_URL = BuildConfig.BASE_URL_BACKEND

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
private val retrofit = Retrofit.Builder()
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface BackApiInter {

    /**
     * Request JWT and User ID from the backend,
     * need to send the googleIdToken in the header,
     * string response is not used.
     */
    @GET("auth")
    suspend fun getAuth(
        @Header("google-id-token") headerGoogleIdToken: String
        //@Body request: LoginRequest
    ): Response<String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object BackApiObject {
    val retrofitService: BackApiInter by lazy {
        retrofit.create(BackApiInter::class.java)
    }
}
