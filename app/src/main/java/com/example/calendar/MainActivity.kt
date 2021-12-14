package com.example.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*


class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar: ActionBar?
        val startTimeCalendar: Calendar = Calendar.getInstance()
        val endTimeCalendar: Calendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)

        val materialCalendar: MaterialCalendarView = findViewById(R.id.materialCalendar)

//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)

        actionBar = supportActionBar
        actionBar?.hide()

        endTimeCalendar.set(Calendar.MONTH, currentMonth) //여기 숫자로 언제까지 달력을 보여줄지 바꿀 수 있음

        materialCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear - 1, currentMonth, 1))
            .setMaximumDate(
                CalendarDay.from(
                    currentYear + 1, currentMonth, //여기 숫자로 언제까지 달력을 보여줄지 바꿀 수 있음
                    endTimeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                )
            )
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        val stCalendarDay =
            CalendarDay(currentYear, currentMonth, (currentDate) - (currentDate - 1))
        val enCalendarDay = CalendarDay(
            endTimeCalendar.get(Calendar.YEAR),
            endTimeCalendar.get(Calendar.MONTH),
            endTimeCalendar.get(Calendar.DATE)
        )

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        val minMaxDecorator = MinMaxDecorator(stCalendarDay, enCalendarDay)
        val boldDecorator = BoldDecorator(stCalendarDay, enCalendarDay)
        val todayDecorator = TodayDecorator(this)
        val eventDecorator = EventDecorator(Color.RED, Collections.singleton(CalendarDay.today()))

        materialCalendar.addDecorators(
            sundayDecorator,
            saturdayDecorator,
            todayDecorator,
            eventDecorator
        )

        binding.menubt.setOnClickListener {
            val menuFragment = MenuFragment()
            val transaction = supportFragmentManager.beginTransaction()

            binding.materialCalendar.setVisibility(View.GONE)
            binding.blank.setVisibility(View.GONE)
            binding.menuwindow.setVisibility(View.GONE)
            transaction.replace(R.id.view, menuFragment)
            transaction.commit()
        }

        binding.calendarbt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        binding.habitbt.setOnClickListener {
            val intent = Intent(this, HabitActivity::class.java)
            startActivity(intent)

        }

        binding.materialCalendar.setOnDateChangedListener {widget, date, selected ->
            val scheduleFragment = ScheduleFragment()
            val bundle = Bundle()
            val transaction = supportFragmentManager.beginTransaction()

            bundle.putString("Year", (date.year).toString())
            bundle.putString("Month", (date.month+1).toString())
            bundle.putString("Day", (date.day).toString())

            scheduleFragment.arguments = bundle

            binding.materialCalendar.setVisibility(View.GONE)
            binding.blank.setVisibility(View.GONE)
            binding.menuwindow.setVisibility(View.GONE)
            transaction.replace(R.id.view, scheduleFragment)
            transaction.commit()

        }
    }

    override fun onDestroy() {
        mBinding = null
        Toast.makeText(this, "메인 액티비티 사라짐", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }
}
