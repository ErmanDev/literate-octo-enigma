// ✅ screens/HourlyForecast.kt
package com.example.weatherapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.model.HourlyWeather
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HourlyForecast(hourlyList: List<HourlyWeather>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        items(hourlyList) { hour ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(80.dp)
                    .background(Color.White.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
                    .padding(8.dp)
            ) {
                Text(
                    text = formatHour(hour.dt),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Icon placeholder removed
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.Gray.copy(alpha = 0.4f), shape = MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    Text("☁", fontSize = 20.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${hour.temp.toInt()}°",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

fun formatHour(unix: Long): String {
    val date = Date(unix * 1000)
    val format = SimpleDateFormat("h a", Locale.getDefault())
    return format.format(date)
}