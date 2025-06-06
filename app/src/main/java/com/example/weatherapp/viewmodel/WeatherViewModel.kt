package com.example.weatherapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> = _weather

    val cityInput = mutableStateOf("")
    val hourlyForecast = mutableStateListOf<HourlyWeather>()

    fun fetchWeather(city: String, apiKey: String) {
        if (apiKey.isBlank()) {
            throw IllegalArgumentException("API key must not be blank")
        }

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCurrentWeather(city, apiKey)
                _weather.value = response
                val lat = response.coord.lat
                val lon = response.coord.lon
                fetchHourlyWeather(lat = lat, lon = lon, apiKey = apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeatherByLocation(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeatherByCoordinates(
                    lat = lat,
                    lon = lon,
                    apiKey = apiKey
                )
                _weather.value = response
                fetchHourlyWeather(lat, lon, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun fetchHourlyWeather(lat: Double, lon: Double, apiKey: String) {
        if (apiKey.isBlank()) {
            throw IllegalArgumentException("API key must not be blank")
        }

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getHourlyWeather(
                    lat = lat,
                    lon = lon,
                    apiKey = apiKey
                )
                hourlyForecast.clear()
                hourlyForecast.addAll(response.hourly.take(6))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchAndFetch(apiKey: String) {
        if (apiKey.isBlank()) {
            throw IllegalArgumentException("API key must not be blank")
        }

        val city = cityInput.value
        if (city.isNotBlank()) {
            fetchWeather(city, apiKey)
        }
    }
}
