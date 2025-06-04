package com.kepes.zoltanseventmanagerfrontend.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kepes.zoltanseventmanagerfrontend.data.CreateSubscriptionRequest
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.model.Event
import com.kepes.zoltanseventmanagerfrontend.service.BackApiObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * Handles the logic between the backend and the 'Event UI'
 *
 * Use "val eventList by eventViewModel.eventListFlow.collectAsState()" to get the list of events in
 * the 'Composable', may be you need to add the import 'androidx.compose.runtime.getValue' for 'by'
 * to work.
 */
class EventViewModel : ViewModel() {
    private val _eventListFlow = MutableStateFlow<List<Event>>(emptyList())

    // StateFlow for the list of events, which is observed by the UI
    val eventListFlow: StateFlow<List<Event>> = _eventListFlow

    /**
     * Adds an event to the eventListFlow
     */
    fun addEvent(event: Event) {
        val currentList = _eventListFlow.value.toMutableList()
        currentList.add(event)
        _eventListFlow.value = currentList.toList()
    }

    /**
     * Adds a list of events to the eventListFlow
     */
    fun addEventList(eventList: List<Event>) {
        val currentList = _eventListFlow.value.toMutableList()
        currentList.addAll(eventList)
        _eventListFlow.value = currentList.toList()
    }

    /**
     * Gets all events from the backend and adds them to the eventListFlow
     */
    fun getAllEvents(userState: LoggedUser) {
        var response: MutableList<Event>?
        viewModelScope.launch {
            try {
                response = BackApiObject.retrofitService.getAllEvents(
                    bearerToken = "Bearer ${userState.jsonWebToken}",
                    userId = userState.idUser
                ).body()
                if (response != null) {
                    addEventList(response!!)
                }
            } catch (e: Exception) {
                Log.e("EVENT FETCH", "Error, fetching events (in 'getAllEvents()'): ${e.message}")
            }
        }
    }

    fun subscribeToEvent(context: Context, userState: LoggedUser, eventId: Long) {
        viewModelScope.launch {
            try {
                Log.i("EVENT SUBSCRIPTION", "Event ID: $eventId, User ID: ${userState.idUser}")
                var response = BackApiObject.retrofitService.subscribeToEvent(
                    bearerToken = "Bearer ${userState.jsonWebToken}",
                    request = CreateSubscriptionRequest(
                        userId = userState.idUser,
                        eventId = eventId
                    )
                )
                if (response.isSuccessful) {
                    Toast.makeText(
                        context,
                        response.headers()["msg"].toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
            }
        }
    }
}