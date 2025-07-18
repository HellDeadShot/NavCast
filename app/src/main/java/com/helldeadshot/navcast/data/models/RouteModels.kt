package com.helldeadshot.navcast.data.models

import org.osmdroid.util.GeoPoint

data class RoutePoint(
    val latitude: Double,
    val longitude: Double,
    val name: String? = null
)

data class RouteData(
    val points: List<RoutePoint>,
    val osmPoints: List<GeoPoint>, // For OpenStreetMap compatibility
    val distance: String,
    val duration: String
)
