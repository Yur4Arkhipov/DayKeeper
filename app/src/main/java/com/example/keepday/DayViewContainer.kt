package com.example.keepday

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    lateinit var day: CalendarDay
    val textView: TextView? = view.findViewById(R.id.dayText)
}

