package com.helldeadshot.navcast.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helldeadshot.navcast.data.models.RouteData
import com.helldeadshot.navcast.data.models.WeatherResponse
import com.helldeadshot.navcast.data.repository.NavCastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class NavCastViewModel @Inject constructor(
    private val repository: NavCastRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NavCastUiState())
    val uiState: StateFlow<NavCastUiState> = _uiState.asStateFlow()

    private val _routeData = MutableStateFlow<RouteData?>(null)
    val routeData: StateFlow<RouteData?> = _routeData.asStateFlow()

    private val _weatherData = MutableStateFlow<Map<String, WeatherResponse>>(emptyMap())
    val weatherData: StateFlow<Map<String, WeatherResponse>> = _weatherData.asStateFlow()

    fun updateOrigin(origin: String) {
        _uiState.value = _uiState.value.copy(origin = origin)
    }

    fun updateDestination(destination: String) {
        _uiState.value = _uiState.value.copy(destination = destination)
    }

    fun getRoute() {
        val origin = _uiState.value.origin.trim()
        val destination = _uiState.value.destination.trim()

        if (origin.isBlank() || destination.isBlank()) {
            _uiState.value = _uiState.value.copy(
                error = "Please enter both origin and destination"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getRoute(origin, destination)
                .onSuccess { route ->
                    _routeData.value = route
                    fetchWeatherForRoute(route)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to find route"
                    )
                }
        }
    }

    private suspend fun fetchWeatherForRoute(route: RouteData) {
        val weatherMap = mutableMapOf<String, WeatherResponse>()

        try {
            // Get weather for key points along the route
            val keyPoints = selectKeyPoints(route.osmPoints)

            keyPoints.forEach { point ->
                repository.getWeatherForLocation(point.latitude, point.longitude)
                    .onSuccess { weather ->
                        weatherMap["${point.latitude},${point.longitude}"] = weather
                    }
                    .onFailure { error ->
                        // Log error but continue with other points
                        println("Failed to get weather for ${point.latitude},${point.longitude}: ${error.message}")
                    }
            }

            _weatherData.value = weatherMap
        } catch (e: Exception) {
            println("Error fetching weather data: ${e.message}")
        } finally {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private fun selectKeyPoints(points: List<GeoPoint>): List<GeoPoint> {
        if (points.isEmpty()) return emptyList()

        val numberOfPoints = minOf(5, points.size)
        val interval = if (points.size > 1) points.size / numberOfPoints else 0

        return (0 until numberOfPoints).map { i ->
            points[minOf(i * interval, points.size - 1)]
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
