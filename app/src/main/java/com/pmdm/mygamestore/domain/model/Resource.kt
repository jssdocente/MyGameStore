package com.pmdm.mygamestore.domain.model

/**
 *  Sealed class que representa el estado de una operación
 *
 * PATRÓN RESOURCE/RESULT:
 * ✅ Manejo explícito de estados (Loading, Success, Error)
 * ✅ Type-safe: El compilador obliga a manejar todos los casos
 * ✅ Errores tipados con información específica
 * ✅ Elimina null checks y excepciones no controladas
 *
 * @param T Tipo de dato que contiene en caso de éxito
 */
sealed class Resource<out T> {

    /**
     * ⏳ Estado: Operación en progreso
     */
    data object Loading : Resource<Nothing>()

    /**
     * ✅ Estado: Operación completada exitosamente
     *
     * @param data Datos obtenidos de la operación
     */
    data class Success<T>(val data: T) : Resource<T>()

    /**
     * ❌ Estado: Operación falló
     *
     * @param error Error específico que ocurrió
     */
    data class Error(val error: AppError) : Resource<Nothing>()
}

/**
 *  Sealed class que representa errores específicos de la app
 *
 * Permite manejar diferentes tipos de errores de forma específica.
 */
sealed class AppError {

    /**
     *  Error de red
     */
    data class NetworkError(val message: String) : AppError()

    /**
     *  Error de base de datos
     */
    data class DatabaseError(val message: String) : AppError()

    /**
     *  Recurso no encontrado (404)
     */
    data object NotFound : AppError()

    /**
     *  No autorizado (401/403)
     */
    data object Unauthorized : AppError()

    /**
     * ⚠️ Error de validación
     */
    data class ValidationError(val message: String) : AppError()

    /**
     * ❓ Error desconocido
     */
    data class Unknown(val message: String) : AppError()
}
