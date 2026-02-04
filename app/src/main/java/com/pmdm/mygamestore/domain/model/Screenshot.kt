package com.pmdm.mygamestore.domain.model

import kotlinx.serialization.Serializable

/**
 * Modelo que representa una captura de pantalla de un juego
 */
@Serializable
data class Screenshot(
    val id: Int,
    val image: String
)
