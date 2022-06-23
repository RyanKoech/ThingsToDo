package com.example.thingstodo.utilities

import java.text.SimpleDateFormat
import java.util.*

object CustomUtility {

    fun getFormattedDateString(date: Date): String{
        val dateFormat = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        return simpleDateFormat.format(date)
    }

    fun getFormattedTimeString(date: Date): String{
        val timeFormat = "HH:mm"
        val simpleDateFormat = SimpleDateFormat(timeFormat)
        return simpleDateFormat.format(date)
    }

}