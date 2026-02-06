package com.pmdm.mygamestore.data.local.dao

import androidx.room.*
import com.pmdm.mygamestore.data.local.entities.LibraryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {
    @Query("SELECT * FROM library WHERE username = :username")
    fun getLibraryForUser(username: String): Flow<List<LibraryEntity>>

    @Query("SELECT * FROM library WHERE username = :username AND status = :status")
    fun getGamesByStatus(username: String, status: String): Flow<List<LibraryEntity>>

    @Query("SELECT * FROM library WHERE username = :username AND gameId = :gameId")
    suspend fun getLibraryEntry(username: String, gameId: Int): LibraryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLibraryEntry(entry: LibraryEntity)

    @Delete
    suspend fun deleteLibraryEntry(entry: LibraryEntity)

    @Query("DELETE FROM library WHERE username = :username AND gameId = :gameId")
    suspend fun deleteLibraryEntry(username: String, gameId: Int)
}
