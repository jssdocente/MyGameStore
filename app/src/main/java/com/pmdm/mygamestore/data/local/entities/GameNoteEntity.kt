package com.pmdm.mygamestore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.pmdm.mygamestore.domain.model.GameProgress

@Entity(tableName = "game_notes")
data class GameNoteEntity(
    @PrimaryKey val gameId: Int,
    val username: String,
    val note: String,
    val progressStatus: GameProgress, // Ahora es Enum
    val lastUpdated: Long
)
