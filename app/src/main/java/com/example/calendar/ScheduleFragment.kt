package com.example.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.calendar.databinding.FragmentScheduleBinding
import org.json.JSONArray


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

        binding.scheduleDate.text =
            arguments?.getString("Year") + "년 " + arguments?.getString("Month") + "월 " + arguments?.getString(
                "Day"
            ) + "일"

        var plan = binding.scheduleplan
        val date = binding.scheduleDate

        val preferences = mainActivity.getSharedPreferences("pref", Context.MODE_PRIVATE)

        var arr : ArrayList<PlanData> = ArrayList()



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

            var jsonArr = JSONArray()

            arr.add(PlanData(plan_data,date_data))
            Log.e("haeun", "arr: $arr")

            for(i in arr){
                jsonArr.put(i)
            }


            var resultArr = jsonArr.toString()

            val editor = preferences?.edit()
            editor?.putString("person", resultArr)
            editor?.apply()

            var result = preferences?.getString("person","")
            Log.e("haeun", "result: $result")

            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(mainActivity, "계획 추가가 완료되었습니다.", Toast.LENGTH_SHORT).show()


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