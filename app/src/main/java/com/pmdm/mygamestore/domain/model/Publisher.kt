package com.pmdm.mygamestore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Publisher(
    val id: Int,
    val name: String,
    val slug: String
)
