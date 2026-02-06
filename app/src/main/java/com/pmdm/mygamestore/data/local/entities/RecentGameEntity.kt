package com.pmdm.mygamestore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_games")
data class RecentGameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val gameId: Int,
    val timestamp: Long
)
