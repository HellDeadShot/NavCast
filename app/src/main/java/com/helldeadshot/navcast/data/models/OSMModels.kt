package com.helldeadshot.navcast.data.models

import com.google.gson.annotations.SerializedName
import org.osmdroid.util.GeoPoint

// Geocoding Models
data class OSMGeocodeResponse(
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("address") val address: OSMAddress?
)

data class OSMAddress(
    @SerializedName("road") val road: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("postcode") val postcode: String?
)

// Routing Models
data class OSMRouteResponse(
    @SerializedName("routes") val routes: List<OSMRoute>,
    @SerializedName("code") val code: String
)

data class OSMRoute(
    @SerializedName("geometry") val geometry: OSMGeometry,
    @SerializedName("legs") val legs: List<OSMRouteLeg>,
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double
)

data class OSMGeometry(
    @SerializedName("coordinates") val coordinates: List<List<Double>>,
    @SerializedName("type") val type: String
)

data class OSMRouteLeg(
    @SerializedName("steps") val steps: List<OSMRouteStep>,
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double
)

data class OSMRouteStep(
    @SerializedName("geometry") val geometry: OSMGeometry,
    @SerializedName("maneuver") val maneuver: OSMManeuver,
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double
)

data class OSMManeuver(
    @SerializedName("location") val location: List<Double>,
    @SerializedName("instruction") val instruction: String
)

// Helper Models
data class OSMPoint(
    val latitude: Double,
    val longitude: Double,
    val name: String? = null
)
