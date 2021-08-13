package com.example.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    var startTimeCalendar = Calendar.getInstance()
//    var endTimeCalendar = Calendar.getInstance()
//
//    val currentYear = startTimeCalendar.get(Calendar.YEAR)
//    val currentMonth = startTimeCalendar.get(Calendar.MONTH)
//    val currentDate = startTimeCalendar.get(Calendar.DATE)
//
//    endTimeCalendar.set(Calendar.MONTH, currentMonth+3)
//
//    materialCalendar.state().edit()
//    .setFirstDayOfWeek(Calendar.SUNDAY)
//    .setMinimumDate(CalendarDay.from(currentYear, currentMonth, 1))
//    .setMaximumDate(CalendarDay.from(currentYear, currentMonth+3, endTimeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
//    .setCalendarDisplayMode(CalendarMode.MONTHS)
//    .commit()
//
//    val stCalendarDay = CalendarDay(currentYear, currentMonth, currentDate)
//    val enCalendarDay = CalendarDay(endTimeCalendar.get(Calendar.YEAR), endTimeCalendar.get(Calendar.MONTH), endTimeCalendar.get(Calendar.DATE))
//
//    val sundayDecorator = SundayDecorator()
//    val saturdayDecorator = SaturdayDecorator()
//    val minMaxDecorator = MinMaxDecorator(stCalendarDay, enCalendarDay)
//    val boldDecorator = BoldDecorator(stCalendarDay, enCalendarDay)
//    val todayDecorator = TodayDecorator(this)
//
//    materialCalendar.addDecorators(sundayDecorator, saturdayDecorator, boldDecorator, minMaxDecorator, todayDecorator)
}

//class TodayDecorator(context:Context):DayViewDecorator {
//    private var date = CalendarDay.today()
//    val drawable = context.resources.getDrawable(R.drawable.style_only_radius_10)
//    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        return day?.equals(date)!!
//    }
//    override fun decorate(view: DayViewFacade?) {
//        view?.setBackgroundDrawable(drawable)
//    }
//}
//
//class SundayDecorator:DayViewDecorator {
//    private val calendar = Calendar.getInstance()
//    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        day?.copyTo(calendar)
//        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
//        return weekDay == Calendar.SUNDAY
//    }
//    override fun decorate(view: DayViewFacade?) {
//        view?.addSpan(object:ForegroundColorSpan(Color.RED){})
//    }
//}
//
//class SaturdayDecorator:DayViewDecorator {
//    private val calendar = Calendar.getInstance()
//    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        day?.copyTo(calendar)
//        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
//        return weekDay == Calendar.SATURDAY
//    }
//    override fun decorate(view: DayViewFacade?) {
//        view?.addSpan(object:ForegroundColorSpan(Color.BLUE){})
//    }
//}
//
//class MinMaxDecorator(min:CalendarDay, max:CalendarDay):DayViewDecorator {
//    val maxDay = max
//    val minDay = min
//    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        return (day?.month == maxDay.month && day.day > maxDay.day)
//                || (day?.month == minDay.month && day.day < minDay.day)
//    }
//    override fun decorate(view: DayViewFacade?) {
//        view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#d2d2d2")){})
//        view?.setDaysDisabled(true)
//    }
//}
//
//class BoldDecorator(min:CalendarDay, max:CalendarDay):DayViewDecorator {
//    val maxDay = max
//    val minDay = min
//    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        return (day?.month == maxDay.month && day.day <= maxDay.day)
//                || (day?.month == minDay.month && day.day >= minDay.day)
//                || (minDay.month < day?.month!! && day.month < maxDay.month)
//    }
//    override fun decorate(view: DayViewFacade?) {
//        view?.addSpan(object: StyleSpan(Typeface.BOLD){})
//        view?.addSpan(object: RelativeSizeSpan(1.4f){})
//    }
//}
