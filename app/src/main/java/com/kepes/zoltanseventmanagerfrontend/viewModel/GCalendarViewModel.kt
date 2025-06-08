package com.kepes.zoltanseventmanagerfrontend.viewModel

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import com.kepes.zoltanseventmanagerfrontend.data.CalDateTime
import java.util.Calendar

fun addCalendarEvent(
    context: Context,
    title: String,
    location: String,
    descShort: String,
    cdt: CalDateTime
) {
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        putExtra(CalendarContract.Events.DESCRIPTION, descShort)

        Log.d("Calendar", "Event title: $title, location: $location, descShort: $descShort, cdt: $cdt " +
                "startYear: ${cdt.startYear}, startMonth: ${cdt.startMonth}, startDay: ${cdt.startDay}" +
                "startHour: ${cdt.startHour}, startMinute: ${cdt.startMinute}")
        // Event time
        val startMillis = Calendar.getInstance().apply {
            set(cdt.startYear, cdt.startMonth, cdt.startDay, cdt.startHour, cdt.startMinute)
        }.timeInMillis
        val endMillis = Calendar.getInstance().apply {
            set(cdt.endYear, cdt.endMonth, cdt.endDay, cdt.endHour, cdt.endMinute)
        }.timeInMillis

        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Google Calendar app not found", Toast.LENGTH_SHORT).show()
        // Handle the case where Google Calendar is not installed
    }
}