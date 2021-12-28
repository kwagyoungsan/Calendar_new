package com.example.calendar

import com.chibatching.kotpref.KotprefModel

object PlanData : KotprefModel() {
    var plan: String by stringPref()
    val start: String by stringPref()
}