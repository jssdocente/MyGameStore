package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.presentation.ui.componentes.BottomNavigationBar
import com.pmdm.mygamestore.presentation.ui.componentes.FilterSystem
import com.pmdm.mygamestore.presentation.ui.componentes.EmptyState
import com.pmdm.mygamestore.presentation.ui.componentes.ErrorMessage
import com.pmdm.mygamestore.presentation.ui.componentes.GameGrid
import com.pmdm.mygamestore.presentation.ui.componentes.LoadingIndicator
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.presentation.viewmodel.HomeViewModel
import com.pmdm.mygamestore.presentation.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // Navegación
    val navStack = LocalNavStack.current

    // Contexto y ViewModel
    val context = LocalContext.current
    val viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = HomeViewModelFactory(context)
    )

    // Estado UI
    val uiState by viewModel.uiState.collectAsState()

    // Para logout (DEBUG)
    val sessionManager = remember { SessionManagerImpl(context) }
    val scope = rememberCoroutineScope()

    // Focus para búsqueda
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            if (uiState.isSearchMode) {
                TopAppBar(
                    title = {
                        TextField(
                            value = uiState.searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            placeholder = {
                                Text(
                                    "Search games...",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleSearchMode() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close search"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            } else {
                TopAppBar(
                    title = {
                        Text(
                            "Game Store",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.toggleFilterVisibility() }) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Toggle filters",
                                tint = if (uiState.isFilterVisible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleSearchMode() }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
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
            // Sistema de filtros animado debajo de la TopBar
            AnimatedVisibility(
                visible = uiState.isFilterVisible,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                FilterSystem(
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelected = { viewModel.onCategorySelected(it) },
                    selectedPlatform = uiState.selectedPlatform,
                    onPlatformSelected = { viewModel.onPlatformSelected(it) },
                    selectedInterval = uiState.selectedInterval,
                    onIntervalSelected = { viewModel.onIntervalSelected(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            // Contenido principal con gestión de estados
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when {
                    // Estado: Loading
                    uiState.isLoading -> {
                        LoadingIndicator()
                    }

                    // Estado: Error
                    uiState.errorMessage != null -> {
                        ErrorMessage(
                            message = uiState.errorMessage!!,
                            onRetry = { viewModel.refreshGames() }
                        )
                    }

                    // Estado: Empty (sin resultados)
                    uiState.games.isEmpty() -> {
                        EmptyState(
                            message = "No games found",
                            onClearFilters = if (uiState.searchQuery.isNotEmpty() || 
                                uiState.selectedCategory != GameCategory.ALL) {
                                { viewModel.clearAllFilters() }
                            } else null
                        )
                    }

                    // Estado: Success (mostrar juegos)
                    else -> {
                        GameGrid(
                            games = uiState.games,
                            onGameClick = { gameId ->
                                navStack.add(AppRoutes.Detail(gameId))
                            }
                        )
                    }
                }

                // Botón de Logout (DEBUG) - Flotante en la esquina
                Button(
                    onClick = {
                        scope.launch {
                            sessionManager.clearSession()
                            navStack.clear()
                            navStack.add(AppRoutes.Login)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.7f),
                        contentColor = Color.White
                    )
                ) {
                    Text("🚪 Logout")
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
