package com.finance.plutus.mobile.app.util

import java.text.SimpleDateFormat
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
fun String.toCalendar(): Calendar {
    val calendar: Calendar = Calendar.getInstance()
    this.toDate()?.let { time ->
        calendar.time = time
    }
    return calendar
}

fun String.toDate(): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.parse(this)
}

fun Date.toFormattedString(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(this)
}