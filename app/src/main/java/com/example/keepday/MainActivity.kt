package com.example.keepday

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.keepday.databinding.ActivityMainBinding
import com.example.keepday.domain.model.DayEvent
import com.example.keepday.ui.detail.EventDetailScreen
import com.example.keepday.ui.home.CalendarViewManager
import com.example.keepday.ui.home.HomeScreenViewModel
import com.example.keepday.ui.home.TimelineTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeScreenViewModel by viewModels()
    private lateinit var calendarViewManager: CalendarViewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        setupWindowInsets()
        setupCalendarView()
        setupCompose(binding)
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupCompose(binding: ActivityMainBinding) {
        binding.composeView.setContent {
            MaterialTheme {
                val state by viewModel.uiState.collectAsState()
                val selectedEvent = remember { mutableStateOf<DayEvent?>(null) }

                if (selectedEvent.value != null) {
                    EventDetailScreen(
                        event = selectedEvent.value!!,
                        onBackClick = { selectedEvent.value = null }
                    )
                } else {
                    TimelineTable(
                        events = state.tasks,
                        onEventClick = { event ->
                            selectedEvent.value = event
                        }
                    )
                }
            }
        }
    }

    private fun setupCalendarView() {
        calendarViewManager = CalendarViewManager(
            activity = this,
            calendarView = binding.calendarView,
            tvMonthYear = binding.tvMonthYear,
            viewModel = viewModel
        )
        calendarViewManager.setup()
    }
}