package com.example.calendar

import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class EventDecorator(
    private val stringProductColor: ArrayList<String>,
    private val dates: MutableSet<CalendarDay>
) : DayViewDecorator {

    private lateinit var colors : IntArray

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        colors = IntArray(stringProductColor.size)
        for (i in stringProductColor.indices){
            colors[i] = Color.parseColor(stringProductColor[i])
        }
        view?.addSpan(CustomMultipleDotSpan(5f,colors))
    }


}