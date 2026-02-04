package com.pmdm.mygamestore.domain.model

import kotlinx.serialization.Serializable

/**
 * Modelo que representa la clasificación ESRB de un juego
 */
@Serializable
data class EsrbRating(
    val id: Int,
    val name: String,
    val slug: String
)
