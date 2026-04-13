package com.example.keepday.domain.repository

import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface SelectedDateHolder {
    val selectedDate: StateFlow<Date>
    suspend fun setDate(date: Date)
}