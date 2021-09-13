package com.example.calendar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.HabitLayoutBinding

class HabitActivity : AppCompatActivity() {
    private var mBinding: HabitLayoutBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = HabitLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menubt.setOnClickListener {
            val intent = Intent(this, HabitActivity::class.java)
            startActivity(intent)
        }

        binding.calendarbt.setOnClickListener {
            val intent = Intent(this, HabitActivity::class.java)
            startActivity(intent)
        }

        binding.habitbt.setOnClickListener {
            val intent = Intent(this, HabitActivity::class.java)
            startActivity(intent)
        }

        binding.settingbt.setOnClickListener {
            val intent = Intent(this, HabitActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}