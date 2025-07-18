package com.helldeadshot.navcast.data.repository

import com.helldeadshot.navcast.BuildConfig
import com.helldeadshot.navcast.data.api.NominatimService
import com.helldeadshot.navcast.data.api.WeatherApiService
import com.helldeadshot.navcast.data.models.OSMGeocodeResponse
import com.helldeadshot.navcast.data.models.RouteData
import com.helldeadshot.navcast.data.models.RoutePoint
import com.helldeadshot.navcast.data.models.WeatherResponse
import com.helldeadshot.navcast.data.models.Weather
import com.helldeadshot.navcast.data.models.Main
import com.helldeadshot.navcast.data.models.Coordinates
import com.helldeadshot.navcast.data.models.Wind
import com.helldeadshot.navcast.data.models.Sys
import org.osmdroid.util.GeoPoint
import kotlin.math.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavCastRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val nominatimService: NominatimService
) {
    // TEMPORARY: Direct API key for testing - REPLACE WITH YOUR ACTUAL KEY
    private val weatherApiKey = "xxxxxxxxxxxxxxxxxxxx"


    suspend fun getRoute(origin: String, destination: String): Result<RouteData> {
        return try {
            // Step 1: Geocode both addresses
            val originGeocode = nominatimService.geocodeAddress(origin)
            val destinationGeocode = nominatimService.geocodeAddress(destination)

            if (originGeocode.isEmpty()) {
                return Result.failure(Exception("Could not find location for: $origin"))
            }

            if (destinationGeocode.isEmpty()) {
                return Result.failure(Exception("Could not find location for: $destination"))
            }

            // Step 2: Extract coordinates
            val originCoord = originGeocode.first()
            val destCoord = destinationGeocode.first()

            val originLat = originCoord.lat.toDouble()
            val originLon = originCoord.lon.toDouble()
            val destLat = destCoord.lat.toDouble()
            val destLon = destCoord.lon.toDouble()

            // Step 3: Create route with real coordinates
            val routeData = createRealRoute(
                originLat, originLon, originCoord.displayName,
                destLat, destLon, destCoord.displayName
            )

            Result.success(routeData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getWeatherForLocation(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            if (weatherApiKey != "PUT_YOUR_ACTUAL_OPENWEATHERMAP_API_KEY_HERE" && weatherApiKey.isNotEmpty()) {
                // Debug: Log API call attempt
                println("🔧 NavCast Debug - Making REAL weather API call for: $lat, $lon")

                val weather = weatherApiService.getWeather(lat, lon, weatherApiKey)
                println("🔧 NavCast Debug - Weather API call successful!")
                Result.success(weather)
            } else {
                // Mock weather for testing UI while API key is being configured
                println("🔧 NavCast Debug - Using mock weather data (API key placeholder not replaced)")
                val mockWeather = WeatherResponse(
                    coord = Coordinates(lat, lon),
                    weather = listOf(Weather(800, "Clear", "clear sky", "01d")),
                    main = Main(
                        temp = 22.5,
                        humidity = 65,
                        pressure = 1013,
                        feelsLike = 24.0
                    ),
                    wind = Wind(speed = 3.2, deg = 180),
                    name = "Mock Location",
                    sys = Sys(country = "US")
                )
                Result.success(mockWeather)
            }
        } catch (e: Exception) {
            println("🔧 NavCast Debug - Weather API call failed: ${e.message}")
            // Still return mock data if real API fails
            val mockWeather = WeatherResponse(
                coord = Coordinates(lat, lon),
                weather = listOf(Weather(500, "Rain", "light rain", "10d")),
                main = Main(
                    temp = 18.0,
                    humidity = 80,
                    pressure = 1010,
                    feelsLike = 17.0
                ),
                wind = Wind(speed = 5.0, deg = 220),
                name = "Error Fallback",
                sys = Sys(country = "US")
            )
            Result.success(mockWeather)
        }
    }

    // ... rest of your existing methods remain exactly the same ...

    private fun createRealRoute(
        originLat: Double, originLon: Double, originName: String,
        destLat: Double, destLon: Double, destName: String
    ): RouteData {
        val routePoints = generateRoutePoints(
            originLat, originLon, originName,
            destLat, destLon, destName
        )

        val geoPoints = routePoints.map { point ->
            GeoPoint(point.latitude, point.longitude)
        }

        val distance = calculateDistance(originLat, originLon, destLat, destLon)
        val duration = estimateDuration(distance)

        return RouteData(
            points = routePoints,
            osmPoints = geoPoints,
            distance = formatDistance(distance),
            duration = formatDuration(duration)
        )
    }

    private fun generateRoutePoints(
        originLat: Double, originLon: Double, originName: String,
        destLat: Double, destLon: Double, destName: String
    ): List<RoutePoint> {
        val points = mutableListOf<RoutePoint>()
        points.add(RoutePoint(originLat, originLon, originName))

        val numberOfIntermediatePoints = 5
        for (i in 1 until numberOfIntermediatePoints) {
            val ratio = i.toDouble() / numberOfIntermediatePoints
            val curve = sin(ratio * Math.PI) * 0.01
            val lat = originLat + (destLat - originLat) * ratio + curve
            val lon = originLon + (destLon - originLon) * ratio
            points.add(RoutePoint(lat, lon, "Route Point $i"))
        }

        points.add(RoutePoint(destLat, destLon, destName))
        return points
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    private fun estimateDuration(distanceKm: Double): Double {
        return distanceKm / 60.0
    }

    private fun formatDistance(distanceKm: Double): String {
        return if (distanceKm >= 1.0) {
            "${distanceKm.toInt()} km"
        } else {
            "${(distanceKm * 1000).toInt()} m"
        }
    }

    private fun formatDuration(durationHours: Double): String {
        val hours = durationHours.toInt()
        val minutes = ((durationHours - hours) * 60).toInt()
        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            else -> "${minutes}m"
        }
    }
}
