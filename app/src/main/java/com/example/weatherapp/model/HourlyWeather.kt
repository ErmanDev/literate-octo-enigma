package com.example.weatherapp.model

data class HourlyWeather(
    val dt: Long,
    val temp: Float,
    val weather: List<Weather>
)