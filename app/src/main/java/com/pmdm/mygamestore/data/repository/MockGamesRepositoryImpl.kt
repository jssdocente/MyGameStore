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

    override fun getGamesByCategory(category: GameCategory): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()

            val filtered = if (category == GameCategory.ALL) {
                dataSource.games
            } else {
                dataSource.games.filter { it.category == category }
            }

            emit(Resource.Success(filtered))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Error filtering by category")))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getGamesByInterval(interval: DateInterval): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()

            val now = LocalDate.now()
            val filtered = when (interval) {
                DateInterval.ALL_TIME -> dataSource.games

                DateInterval.LAST_WEEK -> dataSource.games.filter {
                    val gameDate = LocalDate.parse(it.releaseDate, DateTimeFormatter.ISO_DATE)
                    gameDate.isAfter(now.minusWeeks(1))
                }

                DateInterval.LAST_30_DAYS -> dataSource.games.filter {
                    val gameDate = LocalDate.parse(it.releaseDate, DateTimeFormatter.ISO_DATE)
                    gameDate.isAfter(now.minusDays(30))
                }

                DateInterval.LAST_90_DAYS -> dataSource.games.filter {
                    val gameDate = LocalDate.parse(it.releaseDate, DateTimeFormatter.ISO_DATE)
                    gameDate.isAfter(now.minusDays(90))
                }
            }

            emit(Resource.Success(filtered))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Error filtering by date")))
        }
    }

    override fun getGamesByPlatform(platform: PlatformEnum): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()

            val filtered = if (platform == PlatformEnum.ALL) {
                dataSource.games
            } else {
                dataSource.games.filter { it.platform == platform }
            }

            emit(Resource.Success(filtered))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Error filtering by platform")))
        }
    }

    override fun getGamesByGenres(genres: List<String>): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()

            val filtered = dataSource.games.filter { game ->
                game.genres.any { genre -> 
                    genres.any { it.equals(genre, ignoreCase = true) }
                }
            }

            emit(Resource.Success(filtered))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Error filtering by genres")))
        }
    }

    override fun searchGames(query: String): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()

            val filtered = dataSource.games.filter { game ->
                game.title.contains(query, ignoreCase = true) ||
                game.description.contains(query, ignoreCase = true)
            }

            emit(Resource.Success(filtered))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Error searching games")))
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
