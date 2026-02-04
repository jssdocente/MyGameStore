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
 * @property slug Identificador único en formato URL-friendly
 * @property title Título del juego
 * @property description Descripción detallada del juego
 * @property imageUrl URL de la imagen de portada (cargada con Coil)
 * @property price Precio en formato decimal (ej: 59.99)
 * @property rating Valoración de 0.0 a 5.0 (ej: 4.8)
 * @property releaseDate Fecha de lanzamiento en formato ISO (yyyy-MM-dd): "2024-01-15"
 * @property category Categoría principal del juego (ACTION, RPG, etc.)
 * @property platforms Lista de plataformas en las que está disponible
 * @property genres Lista de géneros del juego
 * @property stores Lista de tiendas donde está disponible
 * @property tags Lista de etiquetas/tags del juego
 * @property screenshots Lista de capturas de pantalla
 * @property metacritic Puntuación de Metacritic (0-100)
 * @property playtime Tiempo de juego promedio en horas
 * @property ratingsCount Número total de valoraciones
 * @property esrbRating Clasificación ESRB del juego
 */
@Serializable
data class Game(
    val id: Int,
    val slug: String? = null,
    val title: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val rating: Double,
    val releaseDate: String,
    val category: GameCategory,
    val platforms: List<Platform> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val stores: List<Store> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val screenshots: List<Screenshot> = emptyList(),
    val metacritic: Int? = null,
    val playtime: Int? = null,
    val ratingsCount: Int? = null,
    val esrbRating: EsrbRating? = null
)
