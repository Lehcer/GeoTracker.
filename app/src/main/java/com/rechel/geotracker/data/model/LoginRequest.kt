
package com.rechel.geotracker.data.model

data class LoginRequest(
    val correo: String,
    val password: String
)

data class LoginResponse(
    val standardResponse: StandardResponse,
    val body: LoginBody?
)

data class StandardResponse(
    val httpCode: Int,
    val message: String
)

data class LoginBody(
    val login: Boolean,
    val id_usuario: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val rol: String,
    val token: String
)
