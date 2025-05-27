package com.example.weatherapp.model

data class WeatherResponse(
    val main: Main,
    val wind: Wind,
    val weather: List<Weather>,
    val name: String,
    val sys: Sys,
    val coord: Coord
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val pressure: Int,
    val humidity: Int
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Sys(
    val sunset: Long
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Wind(
    val speed: Float,
    val deg: Int
)
