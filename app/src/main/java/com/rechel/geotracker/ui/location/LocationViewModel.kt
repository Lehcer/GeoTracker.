
package com.rechel.geotracker.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rechel.geotracker.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LocationState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class LocationViewModel : ViewModel() {

    private val repository = LocationRepository()

    private val _state = MutableStateFlow(LocationState())
    val state = _state.asStateFlow()

    fun saveLocation(
        nombre: String,
        descripcion: String,
        latitud: Double,
        longitud: Double
    ) {
        viewModelScope.launch {
            _state.value = LocationState(isLoading = true)

            try {
                repository.saveLocation(
                    nombre, descripcion, latitud, longitud
                )

                _state.value = LocationState(isSuccess = true)

            } catch (e: Exception) {
                _state.value = LocationState(
                    error = e.message ?: "Error al guardar ubicación"
                )
            }
        }
    }
}
