package com.kepes.zoltanseventmanagerfrontend.data



/**
 * Date of the logged in user
 */
data class LoggedUser(
    var jsonWebToken: String = "",
    var idUser: String = "",
    var name: String = "",
    var email: String = "",
    var pictureUrl: String = "",
    var hasAccount: Boolean = false,
    var isLoggedIn: Boolean = false,
    var isAdmin: Boolean = false,
    var isRootAdmin: Boolean = false
)

