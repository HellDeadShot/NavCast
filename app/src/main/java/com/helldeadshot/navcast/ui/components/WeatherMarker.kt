package com.helldeadshot.navcast.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helldeadshot.navcast.data.models.WeatherResponse

@Composable
fun WeatherMarker(
    weather: WeatherResponse,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(80.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Weather icon placeholder - you can replace with actual weather icons
            Text(
                text = getWeatherEmoji(weather.weather.firstOrNull()?.main ?: ""),
                style = MaterialTheme.typography.headlineMedium
            )

            // Temperature
            Text(
                text = "${weather.main.temp.toInt()}°C",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun getWeatherEmoji(weatherMain: String): String {
    return when (weatherMain.lowercase()) {
        "clear" -> "☀️"
        "clouds" -> "☁️"
        "rain" -> "🌧️"
        "drizzle" -> "🌦️"
        "thunderstorm" -> "⛈️"
        "snow" -> "❄️"
        "mist", "fog" -> "🌫️"
        else -> "🌤️"
    }
}
