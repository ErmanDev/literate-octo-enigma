package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTime(unix: Long): String {
    val date = Date(unix * 1000)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}
