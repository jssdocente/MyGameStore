package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.mygamestore.R
import com.pmdm.mygamestore.presentation.viewmodel.SplashViewModel
import com.pmdm.mygamestore.presentation.viewmodel.SplashViewModelFactory
import kotlinx.coroutines.delay

/**
 * Pantalla Splash que verifica la sesión del usuario
 *
 * @param viewModel ViewModel que gestiona la lógica de verificación de sesión
 * @param onNavigateToLogin Callback para navegar a Login
 * @param onNavigateToHome Callback para navegar a Home
 */
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel(
        factory = SplashViewModelFactory(LocalContext.current)
    ),
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    // Observar el estado de sesión
    val isLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    // Verificar sesión después del delay
    LaunchedEffect(Unit) {
        delay(2000)  // Mostrar logo durante 2 segundos

        // Decidir navegación según estado de sesión
        if (isLoggedIn) {
            onNavigateToHome()  // Usuario ya está logueado
        } else {
            onNavigateToLogin()  // Usuario necesita autenticarse
        }
    }

    // UI del Splash Screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(200.dp)
        )
    }
}