package com.kepes.zoltanseventmanagerfrontend.data

/**
 * Date of the logged in user
 */
data class LoggedUser(
    val jsonWebToken: String = "",
    val idUser: String = "",
    val name: String = "",
    val email: String = "",
    val pictureUrl: String = ""
)

