package com.pmdm.mygamestore.domain.model

/**
 *  Categorías principales de juegos
 *
 * Representa los géneros principales disponibles en el catálogo.
 * Usado para filtrar juegos por categoría.
 */
enum class GameCategory {
    ALL,        // Todas las categorías (sin filtro)
    ACTION,     // Juegos de acción (God of War, Halo)
    ADVENTURE,  // Aventuras (Zelda, Uncharted)
    RPG,        // Role-Playing Games (Witcher, Elden Ring)
    STRATEGY,   // Estrategia (Civilization, Age of Empires)
    SPORTS,     // Deportes (FIFA, Forza)
    SIMULATION, // Simulación (Stardew Valley, Minecraft)
    PUZZLE      // Puzzles (Tetris, Portal)
}

/**
 *  Plataformas de videojuegos
 *
 * Representa las plataformas en las que un juego está disponible.
 * Usado para filtrar juegos por plataforma.
 */
enum class PlatformEnum {
    ALL,         // Todas las plataformas (sin filtro)
    PC,          // Windows, Mac, Linux
    PLAYSTATION, // PS4, PS5
    XBOX,        // Xbox One, Xbox Series X/S
    NINTENDO,    // Nintendo Switch
    MOBILE       // iOS, Android
}

/**
 *  Intervalos de fechas para filtrar lanzamientos
 *
 * Permite filtrar juegos según cuándo fueron lanzados.
 * Útil para secciones como "Novedades de la semana" o "Lanzamientos recientes".
 */
enum class DateInterval {
    ALL_TIME,      // Todos los tiempos (sin filtro de fecha)
    LAST_WEEK,     // Últimos 7 días
    LAST_30_DAYS,  // Último mes
    LAST_90_DAYS   // Últimos 3 meses
}
