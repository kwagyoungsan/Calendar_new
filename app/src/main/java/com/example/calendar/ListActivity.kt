package com.example.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ListLayoutBinding
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
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
        var result = preferences?.getString("person", "")
        var arr: ArrayList<PlanData> = ArrayList()
        var gson = GsonBuilder().create()
        var listType: TypeToken<MutableList<PlanData>> = object : TypeToken<MutableList<PlanData>>(){}

        if(!result.equals("")){
            val resultData: List<PlanData> = gson.fromJson(result, listType.type)

            for (i in resultData.indices) {
                var plan = resultData[i].plan
                var date = resultData[i].date
                var day = resultData[i].day
                var time = resultData[i].time

                arr.add(PlanData(plan, date, day,time))
            }
        }

        val adapter = RecyclerUserAdapter(arr)

        binding.list.adapter = adapter

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
}