
package com.rechel.geotracker.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.aplicacionesweb2026.com/"

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->

                val original = chain.request()
                val token = TokenManager.getToken(appContext)

                val request = original.newBuilder().apply {
                    if (!token.isNullOrBlank()) {
                        header(
                            "Authorization",
                            "Bearer $token"
                        )
                    }
                }.build()

                chain.proceed(request)
            }
            .build()
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
