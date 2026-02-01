package com.pmdm.mygamestore.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pmdm.mygamestore.presentation.ui.screens.DetailScreen
import com.pmdm.mygamestore.presentation.ui.screens.HomeScreen
import com.pmdm.mygamestore.presentation.ui.screens.LibraryScreen
import com.pmdm.mygamestore.presentation.ui.screens.LoginScreen
import com.pmdm.mygamestore.presentation.ui.screens.ProfileScreen
import com.pmdm.mygamestore.presentation.ui.screens.RegisterScreen
import com.pmdm.mygamestore.presentation.ui.screens.SplashScreen
import kotlinx.serialization.Serializable

// Este objeto permitirá a cualquier pantalla acceder al backStack
val LocalNavStack = staticCompositionLocalOf<MutableList<NavKey>> {
    error("No se ha proporcionado el BackStack. Asegúrate de envolver tu contenido en un CompositionLocalProvider.")
}

@Composable
fun AppNavigation() {
    // Gestiona el historial de navegación, comenzando con la pantalla Splash
    val backStack = rememberNavBackStack(AppRoutes.Splash)


    //Envolvemos la navegación con el BackStack, permitiendo obtener el `backStack` desde el contexto.
    CompositionLocalProvider(LocalNavStack provides backStack) {
        // Configura el sistema de navegación de la aplicación
        NavDisplay(
            // Pasa el historial de navegación
            backStack = backStack,
            // Función para manejar el botón de retroceso
            onBack = { backStack.removeLastOrNull() },
            // Define las rutas y pantallas disponibles
            entryProvider = entryProvider {
                // Pantalla inicial de carga
                entry(AppRoutes.Splash) {
                    SplashScreen(
                        onNavigateToLogin = {
                            backStack.clear()
                            backStack.add(AppRoutes.Login)
                        },
                        onNavigateToHome = {
                            backStack.clear()
                            backStack.add(AppRoutes.Home)
                        }
                    )
                }
                // Pantalla de inicio de sesión
                entry(AppRoutes.Login) {
                    val navStack = LocalNavStack.current
                    LoginScreen(
                        onLoginSuccess = {
                            // 🧹 Limpiar historial de navegación
                            navStack.clear()

                            // 🏠 Navegar a Home
                            navStack.add(AppRoutes.Home)
                        },
                        onNavigateToRegister = {
                            navStack.add(AppRoutes.Register)
                        }
                    )
                }
                // Pantalla de registro de usuario
                entry(AppRoutes.Register) {
                    val navStack = LocalNavStack.current
                    RegisterScreen(
                        onRegisterSuccess = {
                            navStack.clear()
                            navStack.add(AppRoutes.Home)
                        },
                        onNavigateToLogin = {
                            navStack.removeLastOrNull()
                            navStack.add(AppRoutes.Login)
                        }
                    )
                }
                // Pantalla principal con catálogo de juegos
                entry(AppRoutes.Home) {
                    HomeScreen()
                }
                // Pantalla de biblioteca personal
                entry(AppRoutes.Library) {
                    LibraryScreen()
                }
                // Pantalla de perfil de usuario
                entry(AppRoutes.Profile) {
                    ProfileScreen()
                }
                // Pantalla de detalles de un juego específico
                entry<AppRoutes.Detail> { route ->
                    DetailScreen(route.gameId)
                }
            }
        )
    }
}
