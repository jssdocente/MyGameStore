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
     *  UC-002: Obtiene juegos filtrados por múltiples criterios
     *
     * @param query Texto de búsqueda
     * @param category Categoría del juego
     * @param platform Plataforma del juego
     * @param interval Intervalo de lanzamiento
     * @return Flow con el estado de la operación y la lista filtrada
     */
    fun getFilteredGames(
        query: String,
        category: GameCategory,
        platform: PlatformEnum,
        interval: DateInterval
    ): Flow<Resource<List<Game>>> {
        return gamesRepository.getFilteredGames(query, category, platform, interval)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        var sortedGames = resource.data
                        if (query.isNotBlank()) {
                            // Ordenar por relevancia si hay búsqueda
                            sortedGames = sortedGames.sortedByDescending { game ->
                                when {
                                    game.title.contains(query, ignoreCase = true) -> 2
                                    game.description.contains(query, ignoreCase = true) -> 1
                                    else -> 0
                                }
                            }
                        } else if (interval != DateInterval.ALL_TIME) {
                            // Ordenar por fecha si hay intervalo
                            sortedGames = sortedGames.sortedByDescending { it.releaseDate }
                        } else if (category != GameCategory.ALL) {
                            // Ordenar por rating si hay categoría
                            sortedGames = sortedGames.sortedByDescending { it.rating }
                        }
                        Resource.Success(sortedGames)
                    }
                    else -> resource
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
                    else -> resource
                }
            }
    }
}
