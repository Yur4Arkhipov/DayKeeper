package com.example.keepday.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepday.domain.usecase.DeleteTaskUseCase
import com.example.keepday.domain.usecase.GetDayDataUseCase
import com.example.keepday.domain.usecase.ObserveSelectedDateUseCase
import com.example.keepday.domain.usecase.SaveTaskUseCase
import com.example.keepday.domain.usecase.SetSelectedDateUseCase
import com.example.keepday.domain.model.DayEvent
import com.example.keepday.domain.model.toDayEvent
import com.example.keepday.domain.model.toTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    observeSelectedDateUseCase: ObserveSelectedDateUseCase,
    private val getDayDataUseCase: GetDayDataUseCase,
    private val setSelectedDateUseCase: SetSelectedDateUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val selectedDate = observeSelectedDateUseCase()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DayKeeperUiState> =
        selectedDate
            .flatMapLatest { selectedDate ->
                getDayDataUseCase(selectedDate)
                    .map { dayData ->
                        DayKeeperUiState(
                            selectedDate = selectedDate,
                            tasks = dayData.map { task -> task.toDayEvent() },
                            isLoading = false
                        )
                    }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                DayKeeperUiState(isLoading = true)
            )

    fun onDateSelected(date: Date) {
        viewModelScope.launch {
            setSelectedDateUseCase(date)
        }
    }

    fun saveEvent(event: DayEvent) {
        val date = selectedDate.value
        val task = event.toTask()
        viewModelScope.launch {
            saveTaskUseCase(date = date, task = task)
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }
}


data class DayKeeperUiState(
    val selectedDate: Date = Date(),
    val tasks: List<DayEvent> = emptyList(),
    val isLoading: Boolean = false
)