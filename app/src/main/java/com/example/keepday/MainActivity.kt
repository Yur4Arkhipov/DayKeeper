package com.example.keepday

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
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
                val context = LocalContext.current

                if (selectedEvent.value != null) {
                    EventDetailScreen(
                        event = selectedEvent.value!!,
                        onBackClick = { selectedEvent.value = null }
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        TimelineTable(
                            events = state.tasks,
                            onEventClick = { event ->
                                selectedEvent.value = event
                            }
                        )

                        FloatingActionButton(
                            onClick = {
                                try {
                                    val jsonString = context.assets.open("tasks.json").bufferedReader().use { it.readText() }
                                    viewModel.importTasksFromJson(jsonString)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Load Asset JSON")
                        }
                    }
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