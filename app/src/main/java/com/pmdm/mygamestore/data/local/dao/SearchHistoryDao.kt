package com.pmdm.mygamestore.data.local.dao

import androidx.room.*
import com.pmdm.mygamestore.data.local.entities.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history WHERE username = :username ORDER BY timestamp DESC LIMIT 5")
    fun getRecentSearches(username: String): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE username = :username AND `query` = :query")
    suspend fun deleteSearch(username: String, query: String)

    @Query("DELETE FROM search_history WHERE username = :username")
    suspend fun clearHistory(username: String)
}
