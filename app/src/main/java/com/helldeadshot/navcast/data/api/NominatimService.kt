package com.helldeadshot.navcast.data.api

import com.helldeadshot.navcast.data.models.OSMGeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimService {

    @GET("search")
    suspend fun geocodeAddress(
        @Query("q") address: String,
        @Query("format") format: String = "json",
        @Query("addressdetails") addressDetails: Int = 1,
        @Query("limit") limit: Int = 1
    ): List<OSMGeocodeResponse>

    @GET("reverse")
    suspend fun reverseGeocode(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("format") format: String = "json",
        @Query("addressdetails") addressDetails: Int = 1
    ): OSMGeocodeResponse
}
