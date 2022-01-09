package com.example.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.calendar.databinding.FragmentScheduleBinding
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.header.view.*
import org.json.JSONArray
import com.prolificinteractive.materialcalendarview.CalendarDay


class ScheduleFragment : DialogFragment() {
    lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentScheduleBinding
    lateinit var calendarDay : CalendarDay


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScheduleBinding.inflate(inflater, container, false)
        binding.scheduleDate.text =
            arguments?.getString("Year") + "년 " + arguments?.getString("Month") + "월 " + arguments?.getString(
                "Day"
            ) + "일"

        var resultAMPM = ""
        var resultHOUR = ""
        var resultMINUTE = ""

        var plan = binding.scheduleplan
        var date = binding.scheduleDate

        val preferences = mainActivity.getSharedPreferences("pref", Context.MODE_PRIVATE)

        val jsonData = preferences.getString("person", "")

        var gson = GsonBuilder().create()

        var listType: TypeToken<MutableList<PlanData>> =
            object : TypeToken<MutableList<PlanData>>() {}
        val arr: ArrayList<PlanData>? = gson.fromJson(jsonData, listType.type)

        var ampmDATA = resources.getStringArray(R.array.spinner_ampm)
        var hourDATA = resources.getStringArray(R.array.spinner_hour)
        var minuteDATA = resources.getStringArray(R.array.spinner_minute)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_ampm,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAmpm.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_hour,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerHour.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_minute,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerMin.adapter = adapter
        }

        binding.spinnerAmpm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                resultAMPM = ampmDATA[position].toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.spinnerHour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                resultHOUR = hourDATA[position].toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.spinnerMin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                resultMINUTE = minuteDATA[position].toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.switchRepeat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.selectDayText.setVisibility(View.VISIBLE)
                binding.selectDayToggle.setVisibility(View.VISIBLE)
            } else {
                binding.selectDayText.setVisibility(View.GONE)
                binding.selectDayToggle.setVisibility(View.GONE)
            }
        }

        binding.cancelBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(mainActivity, "계획 추가가 취소되었습니다.", Toast.LENGTH_SHORT).show()

        }

        binding.checkBt.setOnClickListener {
            var plan_data = plan.text.toString()
            var date_data = date.text.toString()
            var time_data = resultAMPM + " " + resultHOUR + "시 " + resultMINUTE + "분"

            var dayArr = ArrayList<String>(7)

            if (binding.sunday.isChecked) {
                dayArr.add("일")
            }
            if (binding.monday.isChecked) {
                dayArr.add("월")
            }
            if (binding.tuesday.isChecked) {
                dayArr.add("화")
            }
            if (binding.wednesday.isChecked) {
                dayArr.add("수")
            }
            if (binding.thursday.isChecked) {
                dayArr.add("목")
            }
            if (binding.friday.isChecked) {
                dayArr.add("금")
            }
            if (binding.saturday.isChecked) {
                dayArr.add("토")
            }

            if (arr != null) {
                arr?.add(PlanData(plan_data, calendarDay, dayArr, time_data))
                var jsonArr = gson.toJson(arr, listType.type)
                var resultArr = jsonArr.toString()
                val editor = preferences?.edit()
                editor?.putString("person", resultArr)
                editor?.apply()
            } else {
                var planArr: ArrayList<PlanData> = ArrayList<PlanData>(100)
                planArr.add(PlanData(plan_data, calendarDay, dayArr, time_data))
                var jsonArr = gson.toJson(planArr, listType.type)
                var resultArr = jsonArr.toString()
                val editor = preferences?.edit()
                editor?.putString("person", resultArr)
                editor?.apply()
            }

            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(mainActivity, "계획 추가가 완료되었습니다.", Toast.LENGTH_SHORT).show()


        }

        return binding.root
    }

    fun setDate(day: CalendarDay){
        calendarDay = day
    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}