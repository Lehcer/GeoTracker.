
package com.rechel.geotracker.data.remote

import com.rechel.geotracker.data.model.LoginRequest
import com.rechel.geotracker.data.model.LocationRequest
import com.rechel.geotracker.data.model.LocationModel
import retrofit2.http.GET
import com.rechel.geotracker.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")

    suspend fun login(
        @Body request: LoginRequest

    ): LoginResponse

    @POST("api/locations")
    suspend fun saveLocation(
        @Body request: LocationRequest
    ): LocationModel

    @GET("api/locations")
    suspend fun getLocations(): List<LocationModel>


}

