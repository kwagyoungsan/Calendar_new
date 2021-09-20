package com.example.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.calendar.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var _binding: FragmentMenuBinding? = null
//    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val TAG:String = "MenuFragment : "
        Log.e(TAG, "Log ----- MenuFragment")
        val binding = FragmentMenuBinding.inflate(inflater, container, false)
        Log.e(TAG, "Log ----- binding : "+binding)
        binding.textView.text = arguments?.getString("Key")
        Log.e(TAG, "Log ----- key : "+binding.textView.text)
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