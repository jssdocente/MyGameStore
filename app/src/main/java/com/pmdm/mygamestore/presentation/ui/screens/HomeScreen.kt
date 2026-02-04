package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.presentation.ui.componentes.BottomNavigationBar
import com.pmdm.mygamestore.presentation.ui.componentes.CategoryChipsRow
import com.pmdm.mygamestore.presentation.ui.componentes.TextFieldGS
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // Navegación
    val navStack = LocalNavStack.current

    // Para logout (DEBUG)
    val context = LocalContext.current
    val sessionManager = remember { SessionManagerImpl(context) }
    val scope = rememberCoroutineScope()

    // Estado de búsqueda y filtros (temporal, luego vendrá del ViewModel)
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(GameCategory.ALL) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Game Store",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = AppRoutes.Home,
                onNavigate = { route ->
                    when (route) {
                        AppRoutes.Library -> navStack.add(AppRoutes.Library)
                        AppRoutes.Profile -> navStack.add(AppRoutes.Profile)
                        else -> { /* Ya estamos en Home */ }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra de búsqueda
            TextFieldGS(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Search games...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true
            )

            // Fila de filtros de categoría
            CategoryChipsRow(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Contenido principal (por ahora placeholder)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "🎮 Home Screen Ready",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Search: ${searchQuery.ifEmpty { "None" }}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Category: ${selectedCategory.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    // Botón de Logout (DEBUG)
                    Button(
                        onClick = {
                            scope.launch {
                                sessionManager.clearSession()
                                navStack.clear()
                                navStack.add(AppRoutes.Login)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.7f),
                            contentColor = Color.White
                        )
                    ) {
                        Text("🚪 Logout (DEBUG)")
                    }
                }
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
