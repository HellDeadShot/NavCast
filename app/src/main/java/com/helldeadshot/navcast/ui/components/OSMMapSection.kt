package com.helldeadshot.navcast.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.helldeadshot.navcast.data.api.OSMTileService
import com.helldeadshot.navcast.data.models.RouteData
import com.helldeadshot.navcast.data.models.WeatherResponse
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

@Composable
fun OSMMapSection(
    routeData: RouteData?,
    weatherData: Map<String, WeatherResponse>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapView = remember { createMapView(context) }

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            ) { map ->
                updateMapContent(map, routeData, weatherData)
            }

            // Show message when no route is available
            if (routeData == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Enter origin and destination to see route with weather",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            mapView.onDetach()
        }
    }
}

private fun createMapView(context: Context): MapView {
    // Initialize osmdroid configuration
    Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))

    return MapView(context).apply {
        setTileSource(OSMTileService.MAPNIK)
        setMultiTouchControls(true)
        controller.setZoom(10.0)
        controller.setCenter(GeoPoint(40.7128, -74.0060)) // Default to New York
    }
}

private fun updateMapContent(
    mapView: MapView,
    routeData: RouteData?,
    weatherData: Map<String, WeatherResponse>
) {
    // Clear existing overlays
    mapView.overlays.clear()

    // Add route polyline
    routeData?.let { route ->
        if (route.osmPoints.isNotEmpty()) {
            val polyline = Polyline().apply {
                setPoints(route.osmPoints)
                color = android.graphics.Color.BLUE
                width = 8f
            }
            mapView.overlays.add(polyline)

            // Center map on route
            val boundingBox = org.osmdroid.util.BoundingBox.fromGeoPoints(route.osmPoints)
            mapView.zoomToBoundingBox(boundingBox, true, 100)
        }
    }

    // Add weather markers
    weatherData.forEach { (key, weather) ->
        val coordinates = key.split(",")
        if (coordinates.size == 2) {
            val lat = coordinates[0].toDoubleOrNull()
            val lon = coordinates[1].toDoubleOrNull()

            if (lat != null && lon != null) {
                val marker = Marker(mapView).apply {
                    position = GeoPoint(lat, lon)
                    title = weather.name
                    snippet = "${weather.main.temp.toInt()}°C - ${weather.weather.firstOrNull()?.description ?: ""}"
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                mapView.overlays.add(marker)
            }
        }
    }

    mapView.invalidate()
}
