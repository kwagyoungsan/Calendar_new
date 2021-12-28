package com.example.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ListLayoutBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*

class ListActivity : AppCompatActivity() {
    private var mBinding: ListLayoutBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ListLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar: ActionBar?

//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)

        actionBar = supportActionBar
        actionBar?.hide()

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