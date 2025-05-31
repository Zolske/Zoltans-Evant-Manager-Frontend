package com.kepes.zoltanseventmanagerfrontend.data



/**
 * Date of the logged in user
 */
data class LoggedUser(
    var jsonWebToken: String = "",
    var idUser: String = "",
    var hasAccount: Boolean = false,
    var name: String = "",
    var email: String = "",
    var pictureUrl: String = ""
)

