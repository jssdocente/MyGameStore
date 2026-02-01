package com.pmdm.mygamestore.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.repository.AuthRepository
import com.pmdm.mygamestore.data.repository.AuthRepositoryImpl
import com.pmdm.mygamestore.data.repository.LoginResult
import com.pmdm.mygamestore.data.repository.SessionManager
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)

/**
 * ViewModel para gestionar el estado y la lógica de la pantalla de Login
 *
 * @param authRepository Repositorio para operaciones de autenticación
 */
class LoginViewModel(
    context: Context,
    private val authRepository: AuthRepository = AuthRepositoryImpl(),
    private val sessionManager: SessionManager = SessionManagerImpl(context)
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = newUsername,
                errorMessage = null
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword,
                errorMessage = null
            )
        }
    }

    /**
     * Ejecuta el proceso de login usando el Repository
     */
    fun onLoginClick() {
        // Validaciones de UI
        if (_uiState.value.username.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Username cannot be empty") }
            return
        }

        if (_uiState.value.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Password cannot be empty") }
            return
        }

        // Proceso de login
        viewModelScope.launch {
            // Mostrar loading
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Llamar al Repository
            val result = authRepository.login(
                username = _uiState.value.username,
                password = _uiState.value.password
            )

            // Manejar resultado con when exhaustivo
            when (result) {
                is LoginResult.Success -> {
                    _uiState.update {
                        sessionManager.saveSession(result.username)  // guarda la sesión del usuario
                        it.copy(
                            isLoading = false,
                            isLoginSuccessful = true,
                            errorMessage = null
                        )
                    }
                }

                is LoginResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoginSuccessful = false,
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

    fun resetLoginSuccess() {
        _uiState.update { it.copy(isLoginSuccessful = false) }
    }
}


/**
 * Factory para crear LoginViewModel con Context
 */
class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}