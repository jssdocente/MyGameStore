package com.pmdm.mygamestore.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.repository.SessionManager
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel para la pantalla Splash
 *
 * Verifica si el usuario tiene sesión activa para decidir la navegación inicial
 *
 * @param context Contexto para crear SessionManager
 * @param sessionManager Manager para verificar la sesión del usuario
 */
class SplashViewModel(
    context: Context,
    private val sessionManager: SessionManager = SessionManagerImpl(context)
) : ViewModel() {

    /**
     * Estado que indica si el usuario está logueado
     *
     * Flow convertido a StateFlow para:
     * - Tener un valor inicial (false)
     * - Compartir el mismo Flow entre múltiples observadores
     * - Mantener el último valor emitido
     */
    val isUserLoggedIn: StateFlow<Boolean> = sessionManager.isUserLoggedIn()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
}

/**
 * Factory para crear SplashViewModel con Context
 */
class SplashViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}