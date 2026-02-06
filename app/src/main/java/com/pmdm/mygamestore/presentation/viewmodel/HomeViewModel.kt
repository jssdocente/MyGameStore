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
 * Clase que modela el estado de la interfaz de usuario para la pantalla principal.
 *
 * Proporciona información sobre los juegos cargados, el estado de la carga,
 * mensajes de error y el estado actual de la búsqueda y los filtros aplicados.
 *
 * Propiedades:
 * - `games`: Lista de juegos disponibles en la interfaz.
 * - `isLoading`: Indica si el contenido está en proceso de ser cargado.
 * - `errorMessage`: Mensaje de error devuelto en caso de fallo.
 * - `username`: Nombre del usuario actualmente autenticado.
 *
 * Opciones de búsqueda y filtros:
 * - `isSearchMode`: Indica si el modo de búsqueda está activado.
 * - `isFilterVisible`: Indica si el panel de filtros está visible.
 * - `searchQuery`: Texto de búsqueda introducido por el usuario.
 * - `selectedCategory`: Categoría seleccionada para filtrar juegos.
 * - `selectedPlatform`: Plataforma seleccionada para filtrar los juegos.
 * - `selectedInterval`: Intervalo de fechas seleccionado para filtrar juegos.
 */
data class HomeUiState(
    val games: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val username: String? = null,
    
    // Búsqueda y Filtros
    val isSearchMode: Boolean = false,
    val isFilterVisible: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: GameCategory = GameCategory.ALL,
    val selectedPlatform: PlatformEnum = PlatformEnum.ALL,
    val selectedInterval: DateInterval = DateInterval.ALL_TIME,
    val recentSearches: List<String> = emptyList()
)

/**
 * ViewModel para gestionar el estado de la pantalla principal, específicamente para cargar,
 * filtrar y gestionar una lista de juegos, así como también administrar elementos relacionados
 * con la sesión del usuario.
 *
 * Este ViewModel encapsula la lógica de negocio necesaria para interactuar con los datos de juegos,
 * incluidos filtros, modos de búsqueda y control de errores. Además, interactúa con un gestor de sesión
 * para manejar datos del usuario como el nombre de usuario.
 *
 * Principales responsabilidades:
 * - Gestionar el estado inmutable y mutable de la vista utilizando `StateFlow`.
 * - Cargar los juegos aplicando diferentes tipos de filtros, búsqueda y criterios temporales.
 * - Soportar funcionalidades de gestión, incluyendo modos de búsqueda, visibilidad de filtros y limpieza de errores.
 * - Administrar estados de carga y manejo de errores, proporcionando mensajes de error descriptivos basados en diferentes fallos.
 *
 * Dependencias internas utilizadas:
 * - `GamesRepository`: Para obtener la lista de juegos y aplicar filtros sobre estos.
 * - `GameUseCases`: Un conjunto de casos de uso relacionados con juegos.
 * - `SessionManager`: Para gestionar información de la sesión de usuario, como el nombre de usuario.
 *
 * Métodos principales:
 * - `loadGames`: Carga y filtra juegos según los criterios actuales.
 * - `onSearchQueryChange`: Actualiza la query de búsqueda y recarga los juegos.
 * - `toggleSearchMode`: Activa o desactiva el modo de búsqueda y ajusta otros estados con base en esta acción.
 * - `toggleFilterVisibility`: Muestra u oculta el panel de filtros.
 * - `onCategorySelected`, `onPlatformSelected`, `onIntervalSelected`: Aplica filtros específicos y recarga los juegos.
 * - `refreshGames`: Recarga la lista de juegos, útil para acciones como pull-to-refresh.
 * - `clearError`: Limpia cualquier mensaje de error activo.
 * - `clearAllFilters`: Reinicia todos los filtros a sus valores por defecto y recarga los juegos.
 *
 * Esta clase promueve la separación de responsabilidades y coordina entre la capa de datos
 * (repositorios y casos de uso) y la capa de presentación (UI).
 */
class HomeViewModel(
    context: Context
) : ViewModel() {

    //  Dependencias instanciadas directamente (temporal, antes de Koin)
    private val sessionManager: SessionManager = SessionManagerImpl(context)
    private val gamesRepository: GamesRepository = MockGamesRepositoryImpl(sessionManager)
    private val gameUseCases = GameUseCases(gamesRepository)

    //  Estado privado mutable
    private val _uiState = MutableStateFlow(HomeUiState())
    
    //  Estado público inmutable
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUsername()
        loadGames()
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            gameUseCases.getRecentSearches().collect { searches ->
                _uiState.update { it.copy(recentSearches = searches) }
            }
        }
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
            
            val gamesFlow = gameUseCases.getFilteredGames(
                query = currentState.searchQuery,
                category = currentState.selectedCategory,
                platform = currentState.selectedPlatform,
                interval = currentState.selectedInterval
            )

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
        if (query.isNotBlank()) {
            viewModelScope.launch {
                gameUseCases.addSearchQuery(query)
            }
        }
        loadGames()
    }

    fun toggleSearchMode() {
        _uiState.update { 
            val newSearchMode = !it.isSearchMode
            it.copy(
                isSearchMode = newSearchMode,
                // Si abrimos búsqueda, cerramos filtros
                isFilterVisible = if (newSearchMode) false else it.isFilterVisible,
                // Si cerramos la búsqueda, limpiamos la query
                searchQuery = if (!newSearchMode) "" else it.searchQuery
            )
        }
        // Si acabamos de limpiar la búsqueda al cerrar el modo, recargamos juegos
        if (!_uiState.value.isSearchMode) {
            loadGames()
        }
    }

    fun toggleFilterVisibility() {
        _uiState.update { 
            it.copy(isFilterVisible = !it.isFilterVisible)
        }
    }

    fun onCategorySelected(category: GameCategory) {
        _uiState.update { 
            it.copy(selectedCategory = category)
        }
        loadGames()
    }

    fun onPlatformSelected(platform: PlatformEnum) {
        _uiState.update { 
            it.copy(selectedPlatform = platform)
        }
        loadGames()
    }

    fun onIntervalSelected(interval: DateInterval) {
        _uiState.update { 
            it.copy(selectedInterval = interval)
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
