package com.kepes.zoltanseventmanagerfrontend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event (
    @SerialName(value = "id_event")
    var idEvent: Long,
    var title: String,
    @SerialName(value = "description_short")
    var desc_short: String,
    @SerialName(value = "description")
    var desc: String,
    var date: String,
    var time: String,
    var address: String,
    @SerialName(value = "picture_url")
    var pictureUrl: String
)