package com.pmdm.mygamestore.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.pmdm.mygamestore.MyGameStoreApp
import com.pmdm.mygamestore.data.local.MockDataSource
import com.pmdm.mygamestore.data.local.entities.LibraryEntity
import com.pmdm.mygamestore.data.local.entities.RecentGameEntity
import com.pmdm.mygamestore.data.local.entities.SearchHistoryEntity
import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import com.pmdm.mygamestore.domain.model.AppError
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 *  Implementación MOCK del repositorio de juegos con persistencia local mediante Room
 *
 * PROPÓSITO:
 * - Desarrollo sin depender de backend/API
 * - Filtra datos en MEMORIA (no en servidor)
 * - Simula delays de red para testing realista
 * - Integra persistencia local con Room para biblioteca, favoritos y notas
 */
class MockGamesRepositoryImpl(
    private val sessionManager: SessionManager
) : GamesRepository {

    private val dataSource = MockDataSource
    private val db = MyGameStoreApp.database

    private suspend fun getCurrentUser(): String {
        return sessionManager.getUsername().first() ?: "guest"
    }

    private suspend fun simulateNetworkDelay() {
        delay(800)
    }

    override fun getAllGames(): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()
            emit(Resource.Success(dataSource.games))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Unknown error")))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFilteredGames(
        query: String,
        category: GameCategory,
        platform: PlatformEnum,
        interval: DateInterval
    ): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()

            var filtered = dataSource.games

            // Filtro por Query
            if (query.isNotBlank()) {
                filtered = filtered.filter { game ->
                    game.title.contains(query, ignoreCase = true) ||
                            game.description.contains(query, ignoreCase = true)
                }
            }

            // Filtro por Categoría
            if (category != GameCategory.ALL) {
                filtered = filtered.filter { it.category == category }
            }

            // Filtro por Plataforma
            if (platform != PlatformEnum.ALL) {
                filtered = filtered.filter { game ->
                    game.platforms.any { p ->
                        when (platform) {
                            PlatformEnum.PC -> p.slug.contains("pc", ignoreCase = true)
                            PlatformEnum.PLAYSTATION -> p.slug.contains("playstation", ignoreCase = true)
                            PlatformEnum.XBOX -> p.slug.contains("xbox", ignoreCase = true)
                            PlatformEnum.NINTENDO -> p.slug.contains("nintendo", ignoreCase = true)
                            PlatformEnum.MOBILE -> p.slug.contains("android", ignoreCase = true) || p.slug.contains("ios", ignoreCase = true)
                            else -> false
                        }
                    }
                }
            }

            // Filtro por Intervalo
            if (interval != DateInterval.ALL_TIME) {
                val now = LocalDate.now()
                filtered = filtered.filter {
                    val gameDate = LocalDate.parse(it.releaseDate, DateTimeFormatter.ISO_DATE)
                    when (interval) {
                        DateInterval.LAST_WEEK -> gameDate.isAfter(now.minusWeeks(1))
                        DateInterval.LAST_30_DAYS -> gameDate.isAfter(now.minusDays(30))
                        DateInterval.LAST_90_DAYS -> gameDate.isAfter(now.minusDays(90))
                        else -> true
                    }
                }
            }

            emit(Resource.Success(filtered))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Error filtering games")))
        }
    }

    override suspend fun getGameById(id: Int): Resource<Game> {
        return try {
            simulateNetworkDelay()

            val game = dataSource.games.find { it.id == id }

            if (game != null) {
                Resource.Success(game)
            } else {
                Resource.Error(AppError.NotFound)
            }
        } catch (e: Exception) {
            Resource.Error(AppError.Unknown(e.message ?: "Error getting game"))
        }
    }

    // --- Implementación de Biblioteca y Favoritos con Room ---

    override fun getLibraryGames(): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading)
        val user = getCurrentUser()
        db.libraryDao().getLibraryForUser(user).collect { entities ->
            val games = entities.mapNotNull { entity ->
                dataSource.games.find { it.id == entity.gameId }
            }
            emit(Resource.Success(games))
        }
    }

    override fun getFavoriteGames(): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading)
        val user = getCurrentUser()
        db.libraryDao().getGamesByStatus(user, "FAVORITE").collect { entities ->
            val games = entities.mapNotNull { entity ->
                dataSource.games.find { it.id == entity.gameId }
            }
            emit(Resource.Success(games))
        }
    }

    override suspend fun toggleFavorite(gameId: Int): Resource<Unit> {
        return try {
            val user = getCurrentUser()
            val existing = db.libraryDao().getLibraryEntry(user, gameId)
            if (existing != null) {
                db.libraryDao().deleteLibraryEntry(user, gameId)
            } else {
                db.libraryDao().insertLibraryEntry(
                    LibraryEntity(
                        username = user,
                        gameId = gameId,
                        addedDate = System.currentTimeMillis(),
                        status = "FAVORITE"
                    )
                )
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(AppError.Unknown(e.message ?: "Error toggling favorite"))
        }
    }

    override suspend fun isFavorite(gameId: Int): Boolean {
        val user = getCurrentUser()
        return db.libraryDao().getLibraryEntry(user, gameId) != null
    }

    // --- Implementación de Historial y Notas ---

    override fun getRecentSearches(): Flow<List<String>> = flow {
        val user = getCurrentUser()
        db.searchHistoryDao().getRecentSearches(user).map { entities ->
            entities.map { it.query }
        }.collect { emit(it) }
    }

    override suspend fun addSearchQuery(query: String) {
        val user = getCurrentUser()
        db.searchHistoryDao().insertSearch(
            SearchHistoryEntity(
                username = user,
                query = query,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override fun getRecentGames(): Flow<List<Game>> = flow {
        val user = getCurrentUser()
        db.recentGameDao().getRecentGames(user).map { entities ->
            entities.mapNotNull { entity ->
                dataSource.games.find { it.id == entity.gameId }
            }
        }.collect { emit(it) }
    }

    override suspend fun addToRecentGames(gameId: Int) {
        val user = getCurrentUser()
        db.recentGameDao().addRecentGameAndCleanup(
            RecentGameEntity(
                username = user,
                gameId = gameId,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override fun getNoteForGame(gameId: Int): Flow<String?> = flow {
        val user = getCurrentUser()
        db.gameNoteDao().getNoteForGame(user, gameId).map { it?.note }.collect { emit(it) }
    }

    override suspend fun saveNoteForGame(gameId: Int, note: String, status: String) {
        val user = getCurrentUser()
        db.gameNoteDao().insertNote(
            GameNoteEntity(
                gameId = gameId,
                username = user,
                note = note,
                progressStatus = status,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }
}
