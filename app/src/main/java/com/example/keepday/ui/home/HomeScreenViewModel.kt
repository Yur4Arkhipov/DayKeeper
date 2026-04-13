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
import java.time.YearMonth

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
//    private val repository: Repository
): ViewModel() {

    val events = listOf(
        DayEvent("1", 9 * 60 + 30, 60, "Совещание", Color(0xFF4CAF50)),
        DayEvent("2", 11 * 60, 90, "Созвон с клиентом", Color(0xFF2196F3)),
        DayEvent("3", 12 * 60, 60, "Some task", Color(0xFFB18DF1)),
        DayEvent("4", 14 * 60 + 15, 45, "Обед", Color(0xFFFF9800)),
        DayEvent("5", 0 * 60 + 15, 15, "fff", Color(0xFFDAADE1)),
        DayEvent("6", 23 * 60 + 15, 15, "павп", Color(0xFFEEA9A4))
    )

    private val _uiState = MutableStateFlow<UiState<ScheduleUiData>>(UiState.Idle)
    val uiState: StateFlow<UiState<ScheduleUiData>> = _uiState

    init {
        loadEvents()
    }

    private fun loadEvents(/*date: LocalDate*/) {
        _uiState.value = UiState.Loading
        try {
//                val events = repository.getEventsForDate(date)
                val events = events
                val uiData = ScheduleUiData(events = events/*, selectedDate = date*/)
                _uiState.value = UiState.Success(uiData)
//            _events.value = repository.getTodayEvents()
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }

    fun onMonthChanged(yearMonth: YearMonth) {
        // Логика обновления месяца, загрузка событий и т.д.
    }
}


data class ScheduleUiData(
    val events: List<DayEvent> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val currentMonth: YearMonth = YearMonth.now()
)