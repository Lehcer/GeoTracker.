
package com.rechel.geotracker.data.model

import com.google.gson.annotations.SerializedName

data class LocationRequest(
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double
)

data class LocationModel(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double,
    @SerializedName("created_at")
    val createdAt: String? = null
)
