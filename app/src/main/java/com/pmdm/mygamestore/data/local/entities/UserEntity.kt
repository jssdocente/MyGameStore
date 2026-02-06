package com.pmdm.mygamestore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val username: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null,
    val bio: String? = null
)
