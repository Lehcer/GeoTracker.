
package com.rechel.geotracker.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rechel.geotracker.data.model.LocationModel
import com.rechel.geotracker.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LocationsState(
    val isLoading: Boolean = false,
    val locations: List<LocationModel> = emptyList(),
    val error: String? = null,
    val selectedLocation: LocationModel? = null
)

class LocationsViewModel : ViewModel() {

    private val repository = LocationRepository()

    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    fun loadLocations() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val locations = repository.getLocations()

                _state.value = _state.value.copy(
                    isLoading = false,
                    locations = locations
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar ubicaciones"
                )
            }
        }
    }

    fun selectLocation(location: LocationModel) {
        _state.value = _state.value.copy(
            selectedLocation = location
        )
    }
    fun clearSelection() {
        _state.value = _state.value.copy(
            selectedLocation = null
        )
    }

    fun closeDialog() {
        _state.value = _state.value.copy(
            selectedLocation = null
        )
    }
}
