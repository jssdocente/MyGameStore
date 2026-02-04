package com.pmdm.mygamestore.data.repository

import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 *  Interfaz que define el contrato del repositorio de juegos
 *
 * PATRÓN REPOSITORY:
 * ✅ Abstrae la fuente de datos (API, DB, Mock)
 * ✅ Permite múltiples implementaciones
 * ✅ Facilita testing con mocks
 * ✅ Aplica principio de Inversión de Dependencias (SOLID)
 */
interface GamesRepository {

    /**
     * Obtiene todos los juegos disponibles en el catálogo
     */
    fun getAllGames(): Flow<Resource<List<Game>>>

    /**
     * Filtra juegos por categoría
     */
    fun getGamesByCategory(category: GameCategory): Flow<Resource<List<Game>>>

    /**
     * Filtra juegos por intervalo de fecha de lanzamiento
     */
    fun getGamesByInterval(interval: DateInterval): Flow<Resource<List<Game>>>

    /**
     * Filtra juegos por plataforma
     */
    fun getGamesByPlatform(platform: PlatformEnum): Flow<Resource<List<Game>>>

    /**
     * Filtra juegos por géneros
     */
    fun getGamesByGenres(genres: List<String>): Flow<Resource<List<Game>>>

    /**
     * Busca juegos por texto en título o descripción
     */
    fun searchGames(query: String): Flow<Resource<List<Game>>>

    /**
     * Obtiene un juego específico por su ID
     */
    suspend fun getGameById(id: Int): Resource<Game>
}
