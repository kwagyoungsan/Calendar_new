package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.example.calendar.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.*
import java.util.Calendar
import android.util.Log
import android.widget.FrameLayout
import com.kakao.sdk.common.util.Utility


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


        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)


//        val VIEW1: FrameLayout = findViewById(R.id.view)

        actionBar = supportActionBar
        actionBar?.hide()

        materialCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear, currentMonth, 1))
            .setMaximumDate(
                CalendarDay.from(
                    currentYear,
                    currentMonth + 3,
                    endTimeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                )
            )
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        endTimeCalendar.set(Calendar.MONTH, currentMonth + 3)

        val stCalendarDay = CalendarDay(currentYear, currentMonth, currentDate)
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

        materialCalendar.addDecorators(
            sundayDecorator,
            saturdayDecorator,
            boldDecorator,
            minMaxDecorator,
            todayDecorator
        )

        binding.menubt.setOnClickListener {
//            val bundle = Bundle()
            val menuFragment = MenuFragment()
            val transaction = supportFragmentManager.beginTransaction()

//            bundle.putString("Key", "Hello MenuFragment")

//            menuFragment.arguments = bundle
            binding.materialCalendar.setVisibility(View.GONE);
            binding.blank.setVisibility(View.GONE);
            binding.menuwindow.setVisibility(View.GONE);
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

        binding.settingbt.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        mBinding = null

        super.onDestroy()
    }
}
