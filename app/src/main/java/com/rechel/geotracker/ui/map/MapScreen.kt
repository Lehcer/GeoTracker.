
package com.rechel.geotracker.ui.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.rechel.geotracker.ui.locations.LocationsViewModel

@Composable
fun MapScreen(
    viewModel: LocationsViewModel
) {
    val state by viewModel.state.collectAsState()

    val cameraPositionState = rememberCameraPositionState()
    var mapLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadLocations()
    }

    LaunchedEffect(mapLoaded, state.locations) {
        if (mapLoaded && state.locations.isNotEmpty()) {
            val location = state.locations.first()

            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitud, location.longitud),
                    14f
                )
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                mapLoaded = true
            }
        ) {
            state.locations.forEach { location ->
                Marker(
                    state = rememberUpdatedMarkerState(
                        position = LatLng(
                            location.latitud,
                            location.longitud
                        )
                    ),
                    title = location.nombre,
                    onClick = {
                        viewModel.selectLocation(location)
                        true
                    }
                )
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }

    state.selectedLocation?.let { location ->
        AlertDialog(
            onDismissRequest = {
                viewModel.clearSelection()
            },
            title = {
                Text(location.nombre)
            },
            text = {
                Column {
                    Text(location.descripcion)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Latitud: ${location.latitud}")
                    Text("Longitud: ${location.longitud}")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearSelection()
                    }
                ) {
                    Text("Cerrar")
                }
            }
        )
    }
}
