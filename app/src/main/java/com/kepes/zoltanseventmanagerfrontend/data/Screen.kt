package com.kepes.zoltanseventmanagerfrontend.data


sealed class Screen(val rout: String) {
    object Login: Screen("login")
    object UpcomingEvents: Screen("upcoming_events")
    object SubscribedEvents: Screen("subscribed_events")
}