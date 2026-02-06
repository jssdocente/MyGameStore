package com.pmdm.mygamestore.data.local.dao

import androidx.room.*
import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameNoteDao {
    @Query("SELECT * FROM game_notes WHERE username = :username AND gameId = :gameId")
    fun getNoteForGame(username: String, gameId: Int): Flow<GameNoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: GameNoteEntity)

    @Update
    suspend fun updateNote(note: GameNoteEntity)

    @Query("DELETE FROM game_notes WHERE username = :username AND gameId = :gameId")
    suspend fun deleteNote(username: String, gameId: Int)
}
