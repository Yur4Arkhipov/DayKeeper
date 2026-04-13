package com.example.keepday

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.keepday.databinding.ActivityMainBinding
import com.example.keepday.ui.home.DayViewContainer
import com.example.keepday.ui.home.TimelineTable
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        setupWindowInsets()
        setupTimelineTableCompose()
        setupCalendarView()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupTimelineTableCompose() {
        binding.composeView.setContent {
            MaterialTheme {
                TimelineTable()
            }
        }
    }

    private fun setupCalendarView() {
        val calendarView = binding.calendarView
        val tvMonthYear = binding.tvMonthYear

        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                bindDayView(container, data)
            }
        }

        val currentMonth = YearMonth.now()
        calendarView.setup(
            startMonth = currentMonth.minusMonths(100),
            endMonth = currentMonth.plusMonths(100),
            firstDayOfWeek = DayOfWeek.MONDAY
        )

        calendarView.scrollToMonth(currentMonth)

        calendarView.monthScrollListener = {
            calendarView.findFirstVisibleMonth()?.yearMonth?.let { yearMonth ->
                tvMonthYear.text = formatMonthYear(yearMonth)
            }
        }

        tvMonthYear.text = formatMonthYear(currentMonth)
    }

    private fun bindDayView(container: DayViewContainer, data: CalendarDay) {
        container.day = data
        container.textView.text = data.date.dayOfMonth.toString()

        val today = LocalDate.now()

        if (data.position == DayPosition.MonthDate) {
            container.textView.visibility = View.VISIBLE

            if (data.date == today) {
                container.textView.setBackgroundResource(R.drawable.today_circle_bg)
                container.textView.setTextColor(
                    ContextCompat.getColor(this, R.color.white)
                )
                container.textView.alpha = 1f
            } else {
                container.textView.background = null
                container.textView.setTextColor(
                    ContextCompat.getColor(this, R.color.black)
                )
                container.textView.alpha = 1f
            }
        } else {
            container.textView.alpha = 0.3f
            container.textView.background = null
        }
    }

    private fun formatMonthYear(yearMonth: YearMonth): String {
        val locale = Locale.getDefault()
        val monthName = yearMonth.month.getDisplayName(
            TextStyle.FULL_STANDALONE, locale
        )
        return "$monthName ${yearMonth.year}"
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
    }
}