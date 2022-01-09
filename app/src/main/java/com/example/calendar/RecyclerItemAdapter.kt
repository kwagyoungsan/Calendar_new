package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class RecyclerUserAdapter(private val items: ArrayList<PlanData>) : RecyclerView.Adapter<RecyclerUserAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerUserAdapter.ViewHolder, position: Int) {

        val item = items[position]

        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return RecyclerUserAdapter.ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(arr: PlanData) {
            var date = "${arr.date.year}년 ${arr.date.month+1}월 ${arr.date.day}일"
            view.plan_recycler.text = arr.plan
            view.date_recycler.text = date
            if(arr.day.size<1){
                view.day_recycler.text = "반복 없음"
            }else{
                var days = ""
                for(i in 0 until arr.day.size){
                    if(i == arr.day.size-1){
                        days += arr.day[i]
                    }else{
                        days += arr.day[i]+", "
                    }
                }
                view.day_recycler.text = days
            }
            view.time_recycler.text = arr.time

        }
    }
}
