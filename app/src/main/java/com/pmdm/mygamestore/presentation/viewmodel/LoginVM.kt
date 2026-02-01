package com.pmdm.mygamestore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**ﬁ
 * Estado UI para la pantalla de Login
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)

/**
 * ViewModel para gestionar el estado y la lógica de la pantalla de Login
 */
class LoginViewModel : ViewModel() {

    // Estado privado mutable
    private val _uiState = MutableStateFlow(LoginUiState())

    // Estado público inmutable para observar desde la UI
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el username en el estado
     */
    fun onUsernameChange(newUsername: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = newUsername,
                errorMessage = null // Limpia el error cuando el usuario escribe
            )
        }
    }

    /**
     * Actualiza el password en el estado
     */
    fun onPasswordChange(newPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword,
                errorMessage = null // Limpia el error cuando el usuario escribe
            )
        }
    }

    /**
     * Ejecuta el proceso de login
     */
    fun onLoginClick() {
        // Validaciones
        if (_uiState.value.username.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Username cannot be empty") }
            return
        }

        if (_uiState.value.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Password cannot be empty") }
            return
        }

        // Inicia el proceso de login
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // Simula una llamada a la API (reemplaza esto con tu lógica real)
                delay(2000) // Simula latencia de red

                // Simulación de validación (reemplaza con tu lógica real)
                val isValid = validateCredentials(
                    _uiState.value.username,
                    _uiState.value.password
                )

                if (isValid) {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isLoginSuccessful = true,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = "Invalid username or password"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = "Network error: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Simula la validación de credenciales
     * TODO: Reemplazar con la lógica real de autenticación
     */
    private suspend fun validateCredentials(username: String, password: String): Boolean {
        // Simulación - reemplaza con tu repositorio/API real
        return username == "admin" && password == "1234"
    }

    /**
     * Limpia el mensaje de error
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * Resetea el estado de login exitoso (útil para navegación)
     */
    fun resetLoginSuccess() {
        _uiState.update { it.copy(isLoginSuccessful = false) }
    }
}
