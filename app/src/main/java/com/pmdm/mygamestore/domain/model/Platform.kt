package com.pmdm.mygamestore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Platform(
    val id: Int,
    val name: String,
    val slug: String
)
