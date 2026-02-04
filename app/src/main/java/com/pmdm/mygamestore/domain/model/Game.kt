package com.pmdm.mygamestore.domain.model

import kotlinx.serialization.Serializable

/**
 *  Modelo de dominio que representa un juego en el catálogo
 *
 * CARACTERÍSTICAS:
 * - Inmutable (val): No se puede modificar después de creación
 * - Data class: Kotlin genera equals, hashCode, toString, copy automáticamente
 * - Serializable: Puede ser serializado para navegación con parámetros
 * - Domain model: Pertenece a la capa de dominio, no a UI ni datos
 *
 * @property id Identificador único del juego (usado como key en listas)
 * @property title Título del juego
 * @property description Descripción detallada del juego
 * @property imageUrl URL de la imagen de portada (cargada con Coil)
 * @property price Precio en formato decimal (ej: 59.99)
 * @property rating Valoración de 0.0 a 5.0 (ej: 4.8)
 * @property releaseDate Fecha de lanzamiento en formato ISO (yyyy-MM-dd): "2024-01-15"
 * @property category Categoría principal del juego (ACTION, RPG, etc.)
 * @property platform Plataforma en la que está disponible
 * @property genres Lista de géneros asociados (["RPG", "Open World", "Fantasy"])
 */
@Serializable
data class Game(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val rating: Double,
    val releaseDate: String,
    val category: GameCategory,
    val platform: PlatformEnum,
    val genres: List<String>
)
