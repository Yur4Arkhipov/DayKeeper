package com.example.keepday.ui.home

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.keepday.R
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class CalendarViewManager(
    private val activity: Activity,
    private val calendarView: CalendarView,
    private val tvMonthYear: TextView,
    private val viewModel: HomeScreenViewModel
) {
    private var previousSelectedDate: LocalDate? = LocalDate.now()

    fun setup() {
        setupDayBinder()
        setupCalendarRange()
        setupMonthScrollListener()
    }

    private fun setupDayBinder() {
        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                bindDayView(container, data)
            }
        }
    }

    private fun setupCalendarRange() {
        val currentMonth = YearMonth.now()
        calendarView.setup(
            startMonth = currentMonth.minusMonths(100),
            endMonth = currentMonth.plusMonths(100),
            firstDayOfWeek = DayOfWeek.MONDAY
        )
        calendarView.scrollToMonth(currentMonth)
    }

    private fun setupMonthScrollListener() {
        calendarView.monthScrollListener = {
            calendarView.findFirstVisibleMonth()?.yearMonth?.let { yearMonth ->
                tvMonthYear.text = formatMonthYear(yearMonth)
            }
        }

        val currentMonth = YearMonth.now()
        tvMonthYear.text = formatMonthYear(currentMonth)
    }

    private fun bindDayView(container: DayViewContainer, data: CalendarDay) {
        container.day = data
        container.textView.text = data.date.dayOfMonth.toString()

        val today = LocalDate.now()
        val isSelected = viewModel.selectedDate.value == data.date

        if (data.position == DayPosition.MonthDate) {
            container.textView.visibility = View.VISIBLE

            when {
                isSelected -> {
                    container.textView.setBackgroundResource(R.drawable.selected_circle_bg)
                    container.textView.setTextColor(
                        ContextCompat.getColor(activity, R.color.white)
                    )
                    container.textView.alpha = 1f
                }
                data.date == today -> {
                    container.textView.setBackgroundResource(R.drawable.today_circle_bg)
                    container.textView.setTextColor(
                        ContextCompat.getColor(activity, R.color.white)
                    )
                    container.textView.alpha = 1f
                }
                else -> {
                    container.textView.background = null
                    container.textView.setTextColor(
                        ContextCompat.getColor(activity, R.color.black)
                    )
                    container.textView.alpha = 1f
                }
            }

            container.textView.setOnClickListener {
                onDateClicked(data.date)
            }
        } else {
            container.textView.alpha = 0.3f
            container.textView.background = null
        }
    }

    private fun onDateClicked(date: LocalDate) {
        if (previousSelectedDate != null && previousSelectedDate != date) {
            calendarView.notifyDateChanged(previousSelectedDate!!)
        }

        previousSelectedDate = date
        viewModel.selectDate(date)

        calendarView.notifyDateChanged(date)
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