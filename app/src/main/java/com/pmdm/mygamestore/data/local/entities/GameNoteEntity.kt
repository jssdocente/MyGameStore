package com.pmdm.mygamestore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_notes")
data class GameNoteEntity(
    @PrimaryKey val gameId: Int,
    val username: String,
    val note: String,
    val progressStatus: String, // PENDING, PLAYING, COMPLETED, ABANDONED
    val lastUpdated: Long
)
