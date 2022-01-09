package com.example.calendar

import com.prolificinteractive.materialcalendarview.CalendarDay

data class PlanData (
    var plan : String,
    var date : CalendarDay,
    var day : ArrayList<String>,
    var time : String
)