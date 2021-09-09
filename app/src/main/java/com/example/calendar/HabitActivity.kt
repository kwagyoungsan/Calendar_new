package com.example.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ActivityMainBinding

class HabitActivity : AppCompatActivity() {
//    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}