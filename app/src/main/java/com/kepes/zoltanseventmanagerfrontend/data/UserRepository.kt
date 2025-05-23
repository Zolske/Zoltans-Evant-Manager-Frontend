package com.kepes.zoltanseventmanagerfrontend.data

import com.kepes.zoltanseventmanagerfrontend.model.User
import com.kepes.zoltanseventmanagerfrontend.service.UserApiService

interface UserRepository {
    /** Fetches user from userApi */
    suspend fun getUser(idToken: String): User
    // test response
    suspend fun getGreeting(): String
}

/**
 * Network Implementation of Repository that fetch user from userApi.
 */
class NetworkUserRepository(
    private val userApiService: UserApiService
) : UserRepository {
    /** Fetches user from userApi*/
    override suspend fun getUser(idToken: String): User = userApiService.getUser(idToken)
    override suspend fun getGreeting(): String = userApiService.getGreeting()
}