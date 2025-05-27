package com.example.weatherapp.model

data class WeatherResponse(
    val name: String, // City name
    val main: Main,
    val weather: List<Weather>,
    val sys: Sys,
    val coord: Coord
)

data class Main(
    val temp: Float,
    val feels_like: Float
)

data class Weather(
    val main: String,         // e.g., Clear, Rain, Clouds
    val description: String,  // e.g., broken clouds
    val icon: String          // e.g., "04d"
)

data class Sys(
    val sunset: Long
)

data class Coord(
    val lat: Double,
    val lon: Double
)
