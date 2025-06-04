package com.kepes.zoltanseventmanagerfrontend.service

import com.kepes.zoltanseventmanagerfrontend.BuildConfig
import com.kepes.zoltanseventmanagerfrontend.data.CreateSubscriptionRequest
import com.kepes.zoltanseventmanagerfrontend.model.Event
import com.kepes.zoltanseventmanagerfrontend.model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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
    @GET("api/auth")
    suspend fun getAuth(
        @Header("google-id-token") headerGoogleIdToken: String
        //@Body request: LoginRequest
    ): Response<User>

    // all request (except 'api/auth') need to have
    // @Header("Authorization") BearerToken: String

    /**
     * Get user Data from the backend Server,
     */
    @GET("api/user/data")
    suspend fun getUserData(
        @Header("Authorization") bearerToken: String,
        @Header("user-id") userId: String
        //@Body request: LoginRequest
    ): Response<User>

    @GET("api/events/all")
    suspend fun getAllEvents(
        @Header("Authorization") bearerToken: String,
        @Header("user-id") userId: String
    ): Response<MutableList<Event>>

    @POST("api/subscription/subscribe")
    suspend fun subscribeToEvent(
        @Header("Authorization") bearerToken: String,
        @Body request: CreateSubscriptionRequest
    ): Response<Unit>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object BackApiObject {
    val retrofitService: BackApiInter by lazy {
        retrofit.create(BackApiInter::class.java)
    }
}
