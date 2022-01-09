package com.example.calendar

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.time.LocalDate

// 밑에 점
class EventDecorator(
    private val color: Int,
    private val dates: MutableSet<CalendarDay>
) : DayViewDecorator {
//    private lateinit var colors: IntArray

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(5f, color))
    }
}