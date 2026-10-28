
package com.rechel.geotracker.data.repository

import android.content.Context
import com.rechel.geotracker.data.model.LoginRequest
import com.rechel.geotracker.data.remote.RetrofitClient
import com.rechel.geotracker.data.remote.TokenManager

class AuthRepository {

    suspend fun login(
        context: Context,
        correo: String,
        password: String
    ): String {

        val response = RetrofitClient.apiService.login(
            LoginRequest(
                correo = correo,
                password = password
            )
        )

        val token = response.body?.token

        if (response.body?.login != true || token.isNullOrBlank()) {
            throw Exception("No se pudo iniciar sesión")
        }

        TokenManager.saveToken(context, token)

        return token
    }
}
