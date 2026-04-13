package com.example.keepday

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.keepday.core.util.UiState
import com.example.keepday.databinding.ActivityMainBinding
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

                if (state is UiState.Success) {
                    TimelineTable(
                        events = (state as UiState.Success).data.events
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