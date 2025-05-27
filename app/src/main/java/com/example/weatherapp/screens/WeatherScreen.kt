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
import androidx.compose.material.icons.filled.Person

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weather by viewModel.weather.observeAsState()

    val condition = weather?.weather?.firstOrNull()?.main ?: ""
    val backgroundRes = getWeatherBackgroundRes(condition)

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Weather Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${it.main.temp.toInt()}°",
                            style = MaterialTheme.typography.displayLarge
                        )

                        Text(
                            text = it.weather.firstOrNull()?.main ?: "N/A",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

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

                Spacer(modifier = Modifier.height(16.dp))


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Additional Info", style = MaterialTheme.typography.titleMedium)

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Humidity: ${weather?.main?.humidity}%")
                            Text("Pressure: ${weather?.main?.pressure} hPa")
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Wind Speed: ${weather?.wind?.speed} m/s")
                            Text("Wind Dir: ${weather?.wind?.deg}°")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Weather Summary", style = MaterialTheme.typography.titleMedium)

                        Text(
                            text = buildString {
                                append("It’s currently ")
                                append("${weather?.main?.temp?.toInt()}°C with ")
                                append("${weather?.weather?.firstOrNull()?.description?.replaceFirstChar { it.uppercase() }} ")
                                append("in ${weather?.name}. Feels like ${weather?.main?.feels_like?.toInt()}°C. ")
                                append("Wind is ${weather?.wind?.speed} m/s from ${weather?.wind?.deg}°. ")
                                append("Humidity is at ${weather?.main?.humidity}%.")
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEDF4FF)) // soft blue background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Developed by",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF1E3A8A)
                        )

                        val team = listOf("Mae Monterola", "Mitchua Reyes", "Kathleen Macahidhid")

                        team.forEach { name ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color(0xFF2563EB),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF1E293B)
                                )
                            }
                        }
                    }
                }

            } ?: LoadingState()
        }
    }
}

fun getWeatherBackgroundRes(condition: String): Int {
    return when (condition.lowercase()) {
        "clear", "sunny" -> R.drawable.bg_sunny
        "clouds", "cloudy" -> R.drawable.bg_cloudy
        "rain", "drizzle", "thunderstorm" -> R.drawable.bg_rainy
        else -> R.drawable.bg_default
    }
}
