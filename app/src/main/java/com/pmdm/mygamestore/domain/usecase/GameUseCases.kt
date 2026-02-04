package com.pmdm.mygamestore.domain.usecase

import com.pmdm.mygamestore.data.repository.GamesRepository
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 *  Casos de uso agrupados para operaciones con juegos
 *
 * PATRÓN USE CASE:
 * - Encapsula lógica de negocio específica de la aplicación
 * - Orquesta llamadas a uno o más repositories
 * - Transforma datos del dominio para casos de uso específicos
 * - Es independiente del framework (Android, iOS, Web)
 *
 * ORGANIZACIÓN:
 * ✅ Una clase por entidad/funcionalidad (GameUseCases, LibraryUseCases, etc.)
 * ✅ Cada método es un caso de uso concreto
 * ✅ NO usamos operator invoke() (llamada directa al método)
 * ✅ Preparado para inyección de dependencias con Koin
 *
 * MANEJO DE RESOURCE:
 * ✅ Recibe Flow<Resource<List<Game>>> del repository
 * ✅ Aplica lógica solo a Resource.Success
 * ✅ Propaga Loading y Error sin modificar
 * ✅ Devuelve Flow<Resource<List<Game>>> al ViewModel
 *
 * @property gamesRepository Repository para acceder a los datos de juegos
 */
class GameUseCases(
    private val gamesRepository: GamesRepository
) {

    /**
     *  UC-001: Obtiene todos los juegos del catálogo
     *
     * CASO DE USO:
     * - Usuario abre la app
     * - Usuario limpia todos los filtros
     * - Vista por defecto del catálogo
     *
     * LÓGICA DE NEGOCIO:
     * - Obtiene todos los juegos sin filtrar
     * - Sin ordenamiento adicional (orden natural)
     *
     * @return Flow<Resource<List<Game>>>
     *         - Loading: Mientras obtiene datos
     *         - Success: Con lista completa de juegos
     *         - Error: Si falla la operación
     */
    fun getAllGames(): Flow<Resource<List<Game>>> {
        return gamesRepository.getAllGames()
    }

    /**
     *  UC-002: Filtra juegos por categoría y ordena por rating
     *
     * CASO DE USO:
     * - Usuario hace click en chip "RPG"
     * - Usuario navega a sección "Juegos de Acción"
     *
     * LÓGICA DE NEGOCIO:
     * - Repository filtra por categoría
     * - UseCase ordena por rating (mejor valorados primero)
     *
     * @param category Categoría a filtrar (ACTION, RPG, etc.)
     * @return Flow<Resource<List<Game>>> ordenados por rating descendente
     */
    fun getGamesByCategory(category: GameCategory): Flow<Resource<List<Game>>> {
        return gamesRepository.getGamesByCategory(category)
            .map { resource ->
                // Solo aplicar lógica si es Success
                when (resource) {
                    is Resource.Success -> {
                        // Ordenar por rating (mayor a menor)
                        val sortedGames = resource.data.sortedByDescending { it.rating }
                        Resource.Success(sortedGames)
                    }
                    is Resource.Loading -> resource // Propagar sin cambios
                    is Resource.Error -> resource   // Propagar sin cambios
                }
            }
    }

    /**
     *  UC-003: Filtra por intervalo de fecha y ordena por fecha
     *
     * CASOS DE USO:
     * - Sección "Novedades de la semana"
     * - Sección "Lanzamientos del mes"
     * - Filtro "Últimos 90 días"
     *
     * LÓGICA DE NEGOCIO:
     * - Repository filtra por intervalo de fecha
     * - UseCase ordena por fecha (más recientes primero)
     *
     * REGLAS DE NEGOCIO:
     * - LAST_WEEK: Últimos 7 días
     * - LAST_30_DAYS: Último mes
     * - LAST_90_DAYS: Últimos 3 meses
     * - ALL_TIME: Sin filtro de fecha
     *
     * @param interval Intervalo de tiempo
     * @return Flow<Resource<List<Game>>> ordenados por fecha descendente
     */
    fun getGamesInterval(interval: DateInterval): Flow<Resource<List<Game>>> {
        return gamesRepository.getGamesByInterval(interval)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        // Ordenar por fecha de lanzamiento (más recientes primero)
                        val sortedGames = resource.data.sortedByDescending { it.releaseDate }
                        Resource.Success(sortedGames)
                    }
                    is Resource.Loading -> resource
                    is Resource.Error -> resource
                }
            }
    }

    /**
     *  UC-004: Filtra juegos por plataforma
     *
     * CASO DE USO:
     * - Usuario tiene PlayStation y solo quiere juegos compatibles
     * - Filtro de plataforma en la UI
     *
     * LÓGICA DE NEGOCIO:
     * - Repository filtra por plataforma
     * - Sin ordenamiento adicional
     *
     * @param platform Plataforma (PC, PLAYSTATION, XBOX, etc.)
     * @return Flow<Resource<List<Game>>>
     */
    fun getGamesByPlatform(platform: PlatformEnum): Flow<Resource<List<Game>>> {
        return gamesRepository.getGamesByPlatform(platform)
    }

    /**
     * ️ UC-005: Filtra juegos por géneros
     *
     * CASO DE USO:
     * - Usuario busca juegos con etiquetas específicas
     * - Búsqueda multi-género: "RPG" + "Open World"
     *
     * LÓGICA DE NEGOCIO:
     * - Filtro inclusivo (OR): Juego debe tener AL MENOS uno de los géneros
     *
     * @param genres Lista de géneros a buscar
     * @return Flow<Resource<List<Game>>>
     */
    fun getGamesByGenres(genres: List<String>): Flow<Resource<List<Game>>> {
        return gamesRepository.getGamesByGenres(genres)
    }

    /**
     *  UC-006: Busca juegos por texto y ordena por relevancia
     *
     * CASO DE USO:
     * - Usuario escribe "witcher" en la barra de búsqueda
     * - Búsqueda en tiempo real mientras escribe
     *
     * LÓGICA DE NEGOCIO:
     * - Si query está vacío → devuelve todos los juegos
     * - Si no → busca en título y descripción
     * - Ordena por RELEVANCIA (coincidencias en título primero)
     *
     * @param query Texto a buscar (case-insensitive)
     * @return Flow<Resource<List<Game>>> ordenados por relevancia
     */
    fun searchGames(query: String): Flow<Resource<List<Game>>> {
        // Si la búsqueda está vacía, devolver todos
        if (query.isBlank()) {
            return gamesRepository.getAllGames()
        }
        
        return gamesRepository.searchGames(query)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        // Ordenar por relevancia
                        val sortedGames = resource.data.sortedByDescending { game ->
                            when {
                                game.title.contains(query, ignoreCase = true) -> 2
                                game.description.contains(query, ignoreCase = true) -> 1
                                else -> 0
                            }
                        }
                        Resource.Success(sortedGames)
                    }
                    is Resource.Loading -> resource
                    is Resource.Error -> resource
                }
            }
    }

    /**
     *  UC-007: Obtiene un juego específico por ID
     *
     * CASOS DE USO:
     * - Usuario hace click en juego → navega a DetailScreen
     *
     * LÓGICA DE NEGOCIO:
     * - Busca juego por ID único
     * - Devuelve Resource.Success con juego
     * - O Resource.Error con AppError.NotFound
     *
     * @param id Identificador único del juego
     * @return Resource<Game>
     */
    suspend fun getGameById(id: Int): Resource<Game> {
        return gamesRepository.getGameById(id)
    }

    /**
     * ⭐ UC-008: Obtiene juegos mejor valorados
     *
     * CASO DE USO:
     * - Sección "Top Rated" en home
     *
     * LÓGICA DE NEGOCIO:
     * - Obtiene todos los juegos
     * - Filtra: rating >= minRating (por defecto 4.5)
     * - Ordena: por rating descendente
     *
     * @param minRating Rating mínimo (por defecto 4.5 de 5.0)
     * @return Flow<Resource<List<Game>>>
     */
    fun getTopRatedGames(minRating: Double = 4.5): Flow<Resource<List<Game>>> {
        return gamesRepository.getAllGames()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val topGames = resource.data
                            .filter { it.rating >= minRating }
                            .sortedByDescending { it.rating }
                        Resource.Success(topGames)
                    }
                    is Resource.Loading -> resource
                    is Resource.Error -> resource
                }
            }
    }

    /**
     *  UC-009: Filtra juegos por rango de precio
     *
     * CASO DE USO:
     * - Usuario busca juegos baratos (< $20)
     *
     * LÓGICA DE NEGOCIO:
     * - Filtra: price <= maxPrice
     * - Ordena: por precio ascendente (más baratos primero)
     *
     * @param maxPrice Precio máximo en dólares
     * @return Flow<Resource<List<Game>>>
     */
    fun getGamesByPriceRange(maxPrice: Double): Flow<Resource<List<Game>>> {
        return gamesRepository.getAllGames()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val filtered = resource.data
                            .filter { it.price <= maxPrice }
                            .sortedBy { it.price } // Más baratos primero
                        Resource.Success(filtered)
                    }
                    is Resource.Loading -> resource
                    is Resource.Error -> resource
                }
            }
    }

    /**
     *  UC-010: Obtiene juegos populares (trending)
     *
     * CASO DE USO:
     * - Sección "Trending Now"
     *
     * LÓGICA DE NEGOCIO:
     * - Actualmente ordenamos por rating (simulación)
     *
     * @param limit Número máximo de juegos a devolver (por defecto 10)
     * @return Flow<Resource<List<Game>>>
     */
    fun getPopularGames(limit: Int = 10): Flow<Resource<List<Game>>> {
        return gamesRepository.getAllGames()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val popular = resource.data
                            .sortedByDescending { it.rating }
                            .take(limit) // Tomar solo los primeros N
                        Resource.Success(popular)
                    }
                    is Resource.Loading -> resource
                    is Resource.Error -> resource
                }
            }
    }
}
