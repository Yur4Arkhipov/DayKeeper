package com.example.keepday.domain.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

data class DayEvent(
    val id: String,
    val date: LocalDate,
    val startMinute: Int,
    val durationMinutes: Int,
    val title: String,
    val description: String = "",
    val color: Color
)