package com.example.keepday.domain

import androidx.compose.ui.graphics.Color

data class DayEvent(
    val id: String,
    val startMinute: Int,
    val durationMinutes: Int,
    val title: String,
    val color: Color
)