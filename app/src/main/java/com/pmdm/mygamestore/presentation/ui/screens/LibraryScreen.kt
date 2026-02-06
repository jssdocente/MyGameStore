package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.mygamestore.data.repository.MockGamesRepositoryImpl
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.LibraryStatus
import com.pmdm.mygamestore.domain.model.Resource
import com.pmdm.mygamestore.domain.usecase.GameUseCases
import com.pmdm.mygamestore.presentation.ui.componentes.*
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import com.pmdm.mygamestore.presentation.viewmodel.LibraryViewModel
import com.pmdm.mygamestore.presentation.viewmodel.LibraryViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel(
        factory = LibraryViewModelFactory(
            GameUseCases(MockGamesRepositoryImpl(SessionManagerImpl(LocalContext.current)))
        )
    )
) {
    val navStack = LocalNavStack.current
    val uiState by viewModel.uiState.collectAsState()
    var gameToDelete by remember { mutableStateOf<Game?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "My Library",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = AppRoutes.Library,
                onNavigate = { route ->
                    when (route) {
                        AppRoutes.Home -> navStack.removeLastOrNull()
                        AppRoutes.Profile -> {
                            navStack.removeLastOrNull()
                            navStack.add(AppRoutes.Profile)
                        }
                        else -> { /* Ya estamos en Library */ }
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
            // Filtros de Estado
            ScrollableTabRow(
                selectedTabIndex = uiState.selectedFilter.ordinal,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                divider = {},
                indicator = {}
            ) {
                LibraryStatus.entries.filter { it != LibraryStatus.NONE }.forEach { status ->
                    FilterChip(
                        selected = uiState.selectedFilter == status,
                        onClick = { viewModel.onFilterSelected(status) },
                        label = { Text(status.displayName) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (val resource = uiState.libraryGames) {
                    is Resource.Loading -> LoadingIndicator()
                    is Resource.Error -> ErrorMessage(
                        message = "Error loading library",
                        onRetry = { viewModel.loadLibrary() }
                    )
                    is Resource.Success -> {
                        if (resource.data.isEmpty()) {
                            EmptyState(
                                message = "No games found in this category"
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(resource.data, key = { it.id }) { game ->
                                    LibraryGameCard(
                                        game = game,
                                        status = uiState.selectedFilter,
                                        onClick = { navStack.add(AppRoutes.Detail(game.id)) },
                                        onDelete = { gameToDelete = game }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Diálogo de Confirmación
        gameToDelete?.let { game ->
            AlertDialog(
                onDismissRequest = { gameToDelete = null },
                title = { Text("Remove from Library") },
                text = { Text("Are you sure you want to remove ${game.title} from your library?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.removeFromLibrary(game.id)
                            gameToDelete = null
                        }
                    ) {
                        Text("Remove", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { gameToDelete = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}