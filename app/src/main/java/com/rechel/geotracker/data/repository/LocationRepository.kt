
package com.rechel.geotracker.data.repository

import com.rechel.geotracker.data.model.LocationModel
import com.rechel.geotracker.data.model.LocationRequest
import com.rechel.geotracker.data.remote.RetrofitClient

class LocationRepository {

    suspend fun saveLocation(
        nombre: String,
        descripcion: String,
        latitud: Double,
        longitud: Double
    ): LocationModel {
        return RetrofitClient.apiService.saveLocation(
            LocationRequest(
                nombre = nombre,
                descripcion = descripcion,
                latitud = latitud,
                longitud = longitud
            )
        )
    }

    suspend fun getLocations(): List<LocationModel> {
        return RetrofitClient.apiService.getLocations()
    }
}
