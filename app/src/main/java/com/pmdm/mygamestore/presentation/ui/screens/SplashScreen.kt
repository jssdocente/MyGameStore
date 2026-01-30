package com.pmdm.mygamestore.presentation.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import com.pmdm.mygamestore.R
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {

    // Obtenemos el acceso a la navegación
    val navStack = LocalNavStack.current

    // Efecto para navegar automáticamente después de un delay
    LaunchedEffect(Unit) {
        delay(2000) // Espera 2 segundos
        navStack.add(AppRoutes.Home)  // Navega a Home
    }

    // UI del Splash Screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Cambia esto por tu imagen
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(200.dp)
        )
    }
}