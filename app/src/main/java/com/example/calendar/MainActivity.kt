package com.example.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.lang.IllegalArgumentException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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

        val preferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        var result = preferences?.getString("person", "")
        var arr: ArrayList<PlanData> = ArrayList()
        var gson = GsonBuilder().create()
        var listType: TypeToken<MutableList<PlanData>> =
            object : TypeToken<MutableList<PlanData>>() {}

        val materialCalendar: MaterialCalendarView = findViewById(R.id.materialCalendar)

        if (!result.equals("")) {
            val resultData: List<PlanData> = gson.fromJson(result, listType.type)

            Log.e("data", "resultData1: ${resultData}")
            for (i in resultData.indices) {
                var plan = resultData[i].plan
                var date = resultData[i].date
                var day = resultData[i].day
                var time = resultData[i].time
                arr.add(PlanData(plan, date, day, time))

                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                var resultDay = dateFormat.format(date.date)
                var resultDate: Date = dateFormat.parse(resultDay)
                var calendar = Calendar.getInstance()
                calendar.time = resultDate

                val random = Random()
                val num = random.nextInt(256)

                var colorArr: ArrayList<String> = ArrayList()
                colorArr.add("#000000")
                colorArr.add("#f1a039")
                colorArr.add("#FF620E")
                colorArr.add("#d42222")
                colorArr.add("#FF0186")

                for (i in date.day..getDaysInMonth(calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR))) {
                    var thisResultDate: Date = dateFormat.parse("${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${i}")
                    var thisCalendar = Calendar.getInstance()
                    thisCalendar.time = thisResultDate

                    var resultColorArr: ArrayList<String> = ArrayList()
                    var colornum = 0
                    for (plans in resultData.indices) {
                        var planDay = resultData[plans].day

                        for (i in 0 until planDay.size) {

                            if (thisCalendar.get(Calendar.DAY_OF_WEEK).toString() == getDateToInt(planDay)[i]) { // day ??? ???????????? ???(????????? ???????????? ????????? ??????)??? ???????????? ???????????? ?????? ????????? EventDecorator
                                resultColorArr.add(colorArr[colornum]) // ????????????????????? ???????????????
                            }
                        }
                        colornum +=1
                    }

                    var thisCalendarDay: CalendarDay = CalendarDay.from(thisResultDate)
                    var eventDecorator =
                        EventDecorator(resultColorArr, Collections.singleton(thisCalendarDay))
                    materialCalendar.addDecorator(eventDecorator)

                }


                for (i in 0..arr.size) {
                    if (day.size < 1) {
                        var eventDecorator = EventDecorator(colorArr, Collections.singleton(date))
                        materialCalendar.addDecorator(eventDecorator)
                    }

                }

            }
        }

        actionBar = supportActionBar
        actionBar?.hide()

        endTimeCalendar.set(Calendar.MONTH, currentMonth) //?????? ????????? ???????????? ????????? ???????????? ?????? ??? ??????

        materialCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear - 1, currentMonth, 1))
            .setMaximumDate(
                CalendarDay.from(
                    currentYear + 1, currentMonth, //?????? ????????? ???????????? ????????? ???????????? ?????? ??? ??????
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
        val todayDecorator = TodayDecorator(this)

        materialCalendar.addDecorators(
            sundayDecorator,
            saturdayDecorator,
            todayDecorator
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

        binding.listbt.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        binding.materialCalendar.setOnDateChangedListener { widget, date, selected ->
            val scheduleFragment = ScheduleFragment()
            val bundle = Bundle()
            val transaction = supportFragmentManager.beginTransaction()

            bundle.putString("Year", (date.year).toString())
            bundle.putString("Month", (date.month + 1).toString())
            bundle.putString("Day", (date.day).toString())

            scheduleFragment.arguments = bundle
            scheduleFragment.setDate(date)

            binding.materialCalendar.setVisibility(View.GONE)
            binding.blank.setVisibility(View.GONE)
            binding.menuwindow.setVisibility(View.GONE)
            transaction.replace(R.id.view, scheduleFragment)
            transaction.commit()
        }
    }

    override fun onDestroy() {
        mBinding = null
        Toast.makeText(this, "?????? ???????????? ?????????", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }

    fun getDaysInMonth(month: Int, year: Int): Int { // ????????? ?????? ??? ????????? ????????? ???????????? ??????
        return when (month) {
            Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
            Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
            Calendar.FEBRUARY -> if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) 29 else 28 // ????????????
            else -> throw Exception("Exception Month")
        }
    }


    fun getDateToInt(dayName: ArrayList<String>): ArrayList<String> {
        var result: ArrayList<String> = ArrayList()
        for (i in 0 until dayName.size) {
            if (dayName[i] == "???") {
                result.add("1")
            } else if (dayName[i] == "???") {
                result.add("2")
            } else if (dayName[i] == "???") {
                result.add("3")
            } else if (dayName[i] == "???") {
                result.add("4")
            } else if (dayName[i] == "???") {
                result.add("5")
            } else if (dayName[i] == "???") {
                result.add("6")
            } else if (dayName[i] == "???") {
                result.add("7")
            }
        }
        return result
    }
}