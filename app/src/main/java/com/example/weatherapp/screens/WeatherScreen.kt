package com.example.weatherapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.utils.formatTime
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weather by viewModel.weather.observeAsState()

    val condition = weather?.weather?.firstOrNull()?.main ?: ""
    val backgroundRes = getWeatherBackgroundRes(condition)

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌄 Background image
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Weather Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 🌤 Foreground UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔍 Search Input
            OutlinedTextField(
                value = viewModel.cityInput.value,
                onValueChange = { input: String -> viewModel.cityInput.value = input },
                label = { Text("Search City") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    viewModel.searchAndFetch(apiKey = "bd28d9ec6957754817eb653bf0adce81")
                }
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(24.dp))

            weather?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // 📍 Location
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // 🌡️ Temperature
                        Text(
                            text = "${it.main.temp.toInt()}°",
                            style = MaterialTheme.typography.displayLarge
                        )

                        Text(
                            text = it.weather.firstOrNull()?.main ?: "N/A",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // ☀️ Feels like and Sunset
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Feels like: ${it.main.feels_like.toInt()}°")
                            Text("Sunset: ${formatTime(it.sys.sunset)}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HourlyForecast(hourlyList = viewModel.hourlyForecast)
            } ?: LoadingState()
        }
    }
}

// 🎨 Background image by weather condition
fun getWeatherBackgroundRes(condition: String): Int {
    return when (condition.lowercase()) {
        "clear", "sunny" -> R.drawable.bg_sunny
        "clouds", "cloudy" -> R.drawable.bg_cloudy
        "rain", "drizzle", "thunderstorm" -> R.drawable.bg_rainy

        else -> R.drawable.bg_default
    }
}
