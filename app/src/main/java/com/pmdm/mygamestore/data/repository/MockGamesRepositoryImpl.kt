package com.pmdm.mygamestore.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.pmdm.mygamestore.data.local.MockDataSource
import com.pmdm.mygamestore.domain.model.AppError
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 *  Implementación MOCK del repositorio de juegos
 *
 * PROPÓSITO:
 * - Desarrollo sin depender de backend/API
 * - Filtra datos en MEMORIA (no en servidor)
 * - Simula delays de red para testing realista
 */
class MockGamesRepositoryImpl : GamesRepository {

    private val dataSource = MockDataSource

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
}
