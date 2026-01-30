package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.presentation.ui.theme.dimens

@Composable
fun HomeScreen() {

    // Obtenemos el acceso a la navegación
    val navStack = LocalNavStack.current

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
        }
    }
}

// Clase de datos para los items del BottomBar
data class BottomNavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: AppRoutes
)
