
package com.rechel.geotracker.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val fusedClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val capturar: () -> Unit = {
        if (nombre.isBlank()) {
            mensaje = "Ingresa el nombre"
        } else {
            val fine = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val coarse = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (fine || coarse) {
                try {
                    @SuppressLint("MissingPermission")
                    fun obtener() {
                        fusedClient.lastLocation
                            .addOnSuccessListener { location ->
                                if (location != null) {
                                    viewModel.saveLocation(
                                        nombre,
                                        descripcion,
                                        location.latitude,
                                        location.longitude
                                    )
                                } else {
                                    mensaje = "No se encontró ubicación GPS"
                                }
                            }
                            .addOnFailureListener {
                                mensaje = it.message ?: "Error de GPS"
                            }
                    }
                    obtener()
                } catch (e: Exception) {
                    mensaje = e.message ?: "Error"
                }
            } else {
                mensaje = "Debes permitir la ubicación"
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        if (permisos.values.any { it }) {
            capturar()
        } else {
            mensaje = "Permiso de ubicación denegado"
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Registrar ubicación",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val fine = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                val coarse = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (fine || coarse) {
                    capturar()
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Capturar y guardar GPS")
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        if (state.isSuccess) {
            Text("Ubicación guardada correctamente")
        }

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Text(mensaje)
    }
}
