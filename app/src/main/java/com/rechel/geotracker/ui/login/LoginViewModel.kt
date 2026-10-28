
package com.rechel.geotracker.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rechel.geotracker.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = AuthRepository()

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login(correo: String, password: String) {

        viewModelScope.launch {
            _state.value = LoginState(isLoading = true)

            try {
                repository.login(
                    getApplication(),
                    correo,
                    password
                )

                _state.value = LoginState(
                    isSuccess = true
                )

            } catch (e: Exception) {
                _state.value = LoginState(
                    error = e.message ?: "Error de conexión"
                )
            }
        }
    }
}
