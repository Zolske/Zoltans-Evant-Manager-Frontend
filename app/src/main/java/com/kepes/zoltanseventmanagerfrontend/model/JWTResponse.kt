package com.kepes.zoltanseventmanagerfrontend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JWTResponse(
    @SerialName("jsonWebToken")
    var jsonWebToken: String,
    @SerialName("userId")
    var userId: String
)