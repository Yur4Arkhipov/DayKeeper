package com.example.keepday.ui.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.keepday.core.util.UiState
import com.example.keepday.domain.DayEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.Month

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
//    private val repository: Repository
): ViewModel() {

    val events = listOf(
        DayEvent("1", LocalDate.now(), 9 * 60 + 30, 60, "Совещание", Color(0xFF4CAF50)),
        DayEvent("2", LocalDate.now(), 11 * 60, 90, "Созвон с клиентом", Color(0xFF2196F3)),
        DayEvent("3", LocalDate.now(), 12 * 60, 60, "Some task", Color(0xFFB18DF1)),
        DayEvent("4", LocalDate.now(), 14 * 60 + 15, 45, "Обед", Color(0xFFFF9800)),
        DayEvent("5", LocalDate.now(), 0 * 60 + 15, 15, "fff", Color(0xFFDAADE1)),
        DayEvent("6", LocalDate.now(), 23 * 60 + 15, 15, "павп", Color(0xFFEEA9A4)),


        DayEvent("7", LocalDate.of(2026, Month.APRIL, 16), 23 * 60 + 15, 15, "павп", Color(0xFFEEA9A4)),
    )

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _uiState = MutableStateFlow<UiState<ScheduleUiData>>(UiState.Idle)
    val uiState: StateFlow<UiState<ScheduleUiData>> = _uiState

    init {
        loadEvents(LocalDate.now())
    }

    private fun loadEvents(date: LocalDate = LocalDate.now()) {
        _uiState.value = UiState.Loading
        try {
            val filteredEvents = events.filter { it.date == date }
            val uiData = ScheduleUiData(events = filteredEvents, selectedDate = date)
            _uiState.value = UiState.Success(uiData)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        loadEvents(date)
    }
}


data class ScheduleUiData(
    val events: List<DayEvent> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
)