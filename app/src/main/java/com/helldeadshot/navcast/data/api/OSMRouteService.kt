package com.helldeadshot.navcast.data.api

import com.helldeadshot.navcast.data.models.OSMGeocodeResponse
import com.helldeadshot.navcast.data.models.OSMRouteResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OSMRouteService {

    // OSRM Routing API
    @GET("route/v1/driving/{coordinates}")
    suspend fun getRoute(
        @Path("coordinates") coordinates: String,
        @Query("overview") overview: String = "full",
        @Query("geometries") geometries: String = "geojson"
    ): OSMRouteResponse
}

interface OSMGeocodingService {

    // Nominatim Geocoding API
    @GET("search")
    suspend fun geocodeAddress(
        @Query("q") address: String,
        @Query("format") format: String = "json",
        @Query("addressdetails") addressDetails: Int = 1,
        @Query("limit") limit: Int = 1
    ): List<OSMGeocodeResponse>
}
