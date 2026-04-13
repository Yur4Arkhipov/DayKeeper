package com.example.keepday.domain.model

import androidx.compose.ui.graphics.Color
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.abs

data class Task(
    val id: Int,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
)

fun Task.toDayEvent(): DayEvent {
    val startDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateStart), ZoneId.systemDefault())
    val finishDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateFinish), ZoneId.systemDefault())

    val startMinute = startDateTime.hour * 60 + startDateTime.minute

    val durationMinutes = Duration.between(startDateTime, finishDateTime).toMinutes().toInt()

    val colors = listOf(Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFFB18DF1), Color(0xFFFF9800), Color(0xFFDAADE1), Color(0xFFEEA9A4))
    val hash = abs(id.hashCode())
    val eventColor = colors[hash % colors.size]

    return DayEvent(
        id = id.toString(),
        date = startDateTime.toLocalDate(),
        startMinute = startMinute,
        durationMinutes = durationMinutes,
        title = name,
        description = description,
        color = eventColor
    )
}

fun DayEvent.toTask(): Task {
    val startDateTime = date.atTime(startMinute / 60, startMinute % 60)
    val finishDateTime = startDateTime.plusMinutes(durationMinutes.toLong())

    return Task(
        id = id.toIntOrNull() ?: 0,
        dateStart = startDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        dateFinish = finishDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        name = title,
        description = description
    )
}