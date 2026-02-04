package com.pmdm.mygamestore.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.repository.GamesRepository
import com.pmdm.mygamestore.data.repository.MockGamesRepositoryImpl
import com.pmdm.mygamestore.data.repository.SessionManager
import com.pmdm.mygamestore.data.repository.SessionManagerImpl
import com.pmdm.mygamestore.domain.model.AppError
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import com.pmdm.mygamestore.domain.usecase.GameUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 *  Estado UI de la pantalla Home
 */
data class HomeUiState(
    val games: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val username: String? = null,
    
    // Filtros activos
    val searchQuery: String = "",
    val selectedCategory: GameCategory = GameCategory.ALL,
    val selectedPlatform: PlatformEnum = PlatformEnum.ALL,
    val selectedInterval: DateInterval = DateInterval.ALL_TIME,
)

/**
 *  ViewModel para la pantalla Home
 */
class HomeViewModel(
    context: Context
) : ViewModel() {

    //  Dependencias instanciadas directamente (temporal, antes de Koin)
    private val gamesRepository: GamesRepository = MockGamesRepositoryImpl()
    private val gameUseCases = GameUseCases(gamesRepository)
    private val sessionManager: SessionManager = SessionManagerImpl(context)

    //  Estado privado mutable
    private val _uiState = MutableStateFlow(HomeUiState())
    
    //  Estado público inmutable
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUsername()
        loadGames()
    }

    private fun loadUsername() {
        viewModelScope.launch {
            sessionManager.getUsername()
                .catch { exception ->
                    println("Error loading username: ${exception.message}")
                }
                .collect { username ->
                    _uiState.update { it.copy(username = username) }
                }
        }
    }

    fun loadGames() {
        viewModelScope.launch {
            val currentState = _uiState.value
            
            val gamesFlow = when {
                currentState.searchQuery.isNotBlank() -> {
                    gameUseCases.searchGames(currentState.searchQuery)
                }
                currentState.selectedInterval != DateInterval.ALL_TIME -> {
                    gameUseCases.getGamesInterval(currentState.selectedInterval)
                }
                currentState.selectedPlatform != PlatformEnum.ALL -> {
                    gameUseCases.getGamesByPlatform(currentState.selectedPlatform)
                }
                currentState.selectedCategory != GameCategory.ALL -> {
                    gameUseCases.getGamesByCategory(currentState.selectedCategory)
                }
                else -> {
                    gameUseCases.getAllGames()
                }
            }

            gamesFlow.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }
                    
                    is Resource.Success -> {
                        _uiState.update { 
                            it.copy(
                                games = resource.data,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                    
                    is Resource.Error -> {
                        val errorMsg = when (resource.error) {
                            is AppError.NetworkError -> 
                                "No internet connection. Please check your network."
                            is AppError.NotFound -> 
                                "No games found."
                            is AppError.DatabaseError -> 
                                "Database error. Please try again."
                            is AppError.Unauthorized -> 
                                "You need to login to access this content."
                            is AppError.ValidationError -> 
                                resource.error.message
                            is AppError.Unknown -> 
                                resource.error.message
                        }
                        
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = errorMsg
                            )
                        }
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        loadGames()
    }

    fun onCategorySelected(category: GameCategory) {
        _uiState.update { 
            it.copy(
                selectedCategory = category,
                searchQuery = "",
                selectedInterval = DateInterval.ALL_TIME,
                selectedPlatform = PlatformEnum.ALL
            )
        }
        loadGames()
    }

    fun onPlatformSelected(platform: PlatformEnum) {
        _uiState.update { 
            it.copy(
                selectedPlatform = platform,
                searchQuery = "",
                selectedInterval = DateInterval.ALL_TIME,
                selectedCategory = GameCategory.ALL
            )
        }
        loadGames()
    }

    fun onIntervalSelected(interval: DateInterval) {
        _uiState.update { 
            it.copy(
                selectedInterval = interval,
                searchQuery = "",
                selectedCategory = GameCategory.ALL,
                selectedPlatform = PlatformEnum.ALL
            )
        }
        loadGames()
    }

    fun refreshGames() {
        loadGames()
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun clearAllFilters() {
        _uiState.update {
            it.copy(
                searchQuery = "",
                selectedCategory = GameCategory.ALL,
                selectedPlatform = PlatformEnum.ALL,
                selectedInterval = DateInterval.ALL_TIME
            )
        }
        loadGames()
    }
}

/**
 *  Factory para crear HomeViewModel
 */
class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
