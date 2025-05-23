package com.kepes.zoltanseventmanagerfrontend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName(value = "id_user")
    val idUser: String,
    val name: String,
    val email: String,
    @SerialName(value = "picture_url")
    val pictureUrl: String
)
