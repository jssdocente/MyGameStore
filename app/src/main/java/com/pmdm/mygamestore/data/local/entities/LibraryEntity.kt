package com.pmdm.mygamestore.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "library",
    primaryKeys = ["username", "gameId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["username"])]
)
data class LibraryEntity(
    val username: String,
    val gameId: Int,
    val addedDate: Long,
    val status: String // FAVORITE, WISHLIST, OWNED
)
