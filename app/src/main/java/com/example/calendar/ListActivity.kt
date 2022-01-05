package com.example.calendar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ListLayoutBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import org.json.JSONArray
import java.util.*

class ListActivity : AppCompatActivity() {
    private var mBinding: ListLayoutBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ListLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        val preferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        var result = preferences?.getString("person","")

        var arr : ArrayList<PlanData> = ArrayList()

        var arrJson = JSONArray(result)



        for (i in 0 until arrJson.length()) {
            arr.add(PlanData(arrJson.optString(i),arrJson.optString(i)))
        }
        val adapter = RecyclerUserAdapter(arr)
//        mutable로 하면 어댑터가 안되고 arraylist로 하면 스케쥴프레그먼트에서 plandata가 안담아짐
        binding.list.adapter = adapter


        Log.e("haeun", "list_result: $result")

        binding.menubt.setOnClickListener {
            val menuFragment = MenuFragment()
            val transaction = supportFragmentManager.beginTransaction()
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

    }

    override fun onDestroy() {
        mBinding = null
        Toast.makeText(this, "습관 사라짐", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }
}