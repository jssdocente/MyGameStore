package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes

/**
 * Data class que representa un item del BottomNavigationBar
 *
 * @property label Texto que se muestra bajo el icono
 * @property icon Icono del item
 * @property route Ruta de navegación asociada
 */
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: AppRoutes
)

/**
 * Componente BottomNavigationBar reutilizable para toda la app
 *
 * Muestra 3 items: Home, Library y Profile
 * Gestiona la navegación entre pantallas principales
 *
 * @param currentRoute Ruta actual seleccionada
 * @param onNavigate Callback para navegar a una ruta
 * @param modifier Modificador para personalizar el contenedor
 */
@Composable
fun BottomNavigationBar(
    currentRoute: AppRoutes,
    onNavigate: (AppRoutes) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, AppRoutes.Home),
        BottomNavItem("Library", Icons.Filled.Favorite, AppRoutes.Library),
        BottomNavItem("Profile", Icons.Filled.Person, AppRoutes.Profile)
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        onNavigate(item.route)
                    }
                }
            )
        }
    }
}
