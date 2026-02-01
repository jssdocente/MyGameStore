package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation3.runtime.NavBackStack
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    // Obtenemos el acceso a la navegación
    val navStack = LocalNavStack.current

    // Para logout
    val context = LocalContext.current
    val sessionManager = remember { SessionManagerImpl(context) }
    val scope = rememberCoroutineScope()

    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, AppRoutes.Home),
        BottomNavItem("Library", Icons.Filled.Favorite, AppRoutes.Library),
        BottomNavItem("Profile", Icons.Filled.Person, AppRoutes.Profile)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (item.route) {
                                AppRoutes.Home -> { /* Ya estamos en Home */ }
                                AppRoutes.Library -> navStack.add(AppRoutes.Library)
                                AppRoutes.Profile -> navStack.add(AppRoutes.Profile)
                                else -> {}
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(MaterialTheme.dimens.paddingMedium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Home Screen - Catálogo de Juegos",
                style = MaterialTheme.typography.headlineMedium
            )

            // Botón de Logout (DEBUG) en la parte inferior
            Button(
                onClick = {
                    scope.launch {
                        // 1. Limpiar sesión en DataStore
                        sessionManager.clearSession()

                        // 2. Limpiar historial de navegación
                        navStack.clear()

                        // 3. Navegar a Login
                        navStack.add(AppRoutes.Login)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeightMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.7f),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "🚪 Logout (DEBUG)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

// Clase de datos para los items del BottomBar
data class BottomNavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: AppRoutes
)
