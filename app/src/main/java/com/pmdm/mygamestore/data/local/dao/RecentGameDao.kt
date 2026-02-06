package com.pmdm.mygamestore.data.local.dao

import androidx.room.*
import com.pmdm.mygamestore.data.local.entities.RecentGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentGameDao {
    @Query("SELECT * FROM recent_games WHERE username = :username ORDER BY timestamp DESC LIMIT 10")
    fun getRecentGames(username: String): Flow<List<RecentGameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentGame(recentGame: RecentGameEntity)

    @Query("DELETE FROM recent_games WHERE username = :username AND id NOT IN (SELECT id FROM recent_games WHERE username = :username ORDER BY timestamp DESC LIMIT 10)")
    suspend fun deleteOldRecentGames(username: String)

    @Transaction
    suspend fun addRecentGameAndCleanup(recentGame: RecentGameEntity) {
        insertRecentGame(recentGame)
        deleteOldRecentGames(recentGame.username)
    }
}
