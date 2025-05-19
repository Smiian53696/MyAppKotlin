package com.example.myapplication_new.gymcalendar



import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast


fun addToCalendar(context: Context, title: String, description: String) {
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.DESCRIPTION, description)
        putExtra(CalendarContract.Events.EVENT_LOCATION, "")
        putExtra(CalendarContract.Events.ALL_DAY, true)


        val startMillis = System.currentTimeMillis()
        val endMillis = startMillis + 60 * 60 * 1000
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Could not open calendar", Toast.LENGTH_SHORT).show()
    }
}
