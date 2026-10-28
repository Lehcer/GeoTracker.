
package com.rechel.geotracker.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    onCapture: () -> Unit,
    onList: () -> Unit,
    onMap: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido a GeoTracker",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onCapture,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Capturar ubicación")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onList,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver ubicaciones")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onMap,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver mapa")
        }
    }
}
