package com.example.calendar

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.DialogFragment
import com.example.calendar.databinding.FragmentScheduleBinding
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import io.github.muddz.styleabletoast.StyleableToast


class ScheduleFragment : DialogFragment() {
    lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentScheduleBinding



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScheduleBinding.inflate(inflater, container, false)

        val ampm = resources.getStringArray(R.array.spinner_ampm)
        val hour = resources.getStringArray(R.array.spinner_hour)
        val minute = resources.getStringArray(R.array.spinner_minute)

        val adapter_ampm = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, ampm)
        val adapter_hour = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, hour)
        val adapter_min = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, minute)

        binding.spinnerAmpm.adapter = adapter_ampm
        binding.spinnerHour.adapter = adapter_hour
        binding.spinnerMin.adapter = adapter_min

        binding.switch1.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked){
                binding.selectDayText.setVisibility(View.VISIBLE)
                binding.selectDayToggle.setVisibility(View.VISIBLE)
            } else {
                binding.selectDayText.setVisibility(View.GONE)
                binding.selectDayToggle.setVisibility(View.GONE)
            }
        }

        binding.cancelBt.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            StyleableToast.makeText(mainActivity, "계획 추가가 취소되었습니다.", Toast.LENGTH_LONG, R.style.mytoast).show()

        }

        binding.checkBt.setOnClickListener{

        }

        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}