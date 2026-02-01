package com.pmdm.mygamestore.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.repository.AuthRepository
import com.pmdm.mygamestore.data.repository.AuthRepositoryImpl
import com.pmdm.mygamestore.data.repository.RegisterResult
import com.pmdm.mygamestore.data.repository.SessionManager
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Estado UI para la pantalla de Register
 */
data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegisterSuccessful: Boolean = false
)

/**
 * ViewModel para gestionar el estado y la lógica de la pantalla de Registro
 */
class RegisterViewModel(
    context: Context,
    private val authRepository: AuthRepository = AuthRepositoryImpl(),
    private val sessionManager: SessionManager = SessionManagerImpl(context)
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _uiState.update { it.copy(username = newUsername, errorMessage = null) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, errorMessage = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, errorMessage = null) }
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = newConfirmPassword, errorMessage = null) }
    }

    /**
     * Ejecuta el proceso de registro con validaciones
     */
    fun onRegisterClick() {
        val state = _uiState.value

        // Validación: Username
        if (state.username.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Username cannot be empty") }
            return
        }
        if (state.username.length < 3) {
            _uiState.update { it.copy(errorMessage = "Username must be at least 3 characters") }
            return
        }

        // Validación: Email
        if (state.email.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email cannot be empty") }
            return
        }
        if (!state.email.contains("@") || !state.email.contains(".")) {
            _uiState.update { it.copy(errorMessage = "Invalid email format") }
            return
        }

        // Validación: Password
        if (state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Password cannot be empty") }
            return
        }
        if (state.password.length < 6) {
            _uiState.update { it.copy(errorMessage = "Password must be at least 6 characters") }
            return
        }

        // Validación: Confirm Password
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Passwords do not match") }
            return
        }

        // Proceso de registro
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.register(
                username = state.username,
                email = state.email,
                password = state.password
            )

            when (result) {
                is RegisterResult.Success -> {
                    // Guardar sesión automáticamente tras registro
                    sessionManager.saveSession(result.username)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRegisterSuccessful = true
                        )
                    }
                }

                is RegisterResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetRegisterSuccess() {
        _uiState.update { it.copy(isRegisterSuccessful = false) }
    }
}


/**
 * Factory para crear RegisterViewModel con Context
 */
class RegisterViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
