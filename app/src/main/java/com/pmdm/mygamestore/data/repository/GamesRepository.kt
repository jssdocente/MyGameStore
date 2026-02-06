package com.pmdm.mygamestore.data.repository

import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import com.pmdm.mygamestore.domain.model.LibraryStatus
import com.pmdm.mygamestore.domain.model.GameProgress
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
     * Busca juegos combinando múltiples criterios de filtrado
     */
    fun getFilteredGames(
        query: String = "",
        category: GameCategory = GameCategory.ALL,
        platform: PlatformEnum = PlatformEnum.ALL,
        interval: DateInterval = DateInterval.ALL_TIME
    ): Flow<Resource<List<Game>>>

    /**
     * Obtiene un juego específico por su ID
     */
    suspend fun getGameById(id: Int): Resource<Game>

    // --- Funcionalidades de Biblioteca y Favoritos (Room) ---

    fun getLibraryGames(status: LibraryStatus = LibraryStatus.ALL): Flow<Resource<List<Game>>>
    
    fun getFavoriteGames(): Flow<Resource<List<Game>>>

    suspend fun toggleFavorite(gameId: Int): Resource<Unit>

    fun isFavorite(gameId: Int): Flow<Boolean>

    suspend fun toggleWishlist(gameId: Int): Resource<Unit>

    fun isInWishlist(gameId: Int): Flow<Boolean>

    suspend fun removeFromLibrary(gameId: Int): Resource<Unit>

    // --- Funcionalidades de Historial y Notas ---

    fun getRecentSearches(): Flow<List<String>>

    suspend fun addSearchQuery(query: String)

    fun getRecentGames(): Flow<List<Game>>

    suspend fun addToRecentGames(gameId: Int)

    fun getNoteForGame(gameId: Int): Flow<String?>

    fun getProgressForGame(gameId: Int): Flow<GameProgress>

    suspend fun saveNoteForGame(gameId: Int, note: String, status: GameProgress)
}
