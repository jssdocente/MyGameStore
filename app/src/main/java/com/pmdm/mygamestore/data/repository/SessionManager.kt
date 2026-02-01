package com.pmdm.mygamestore.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * 📋 Interfaz que define las operaciones de gestión de sesión
 *
 * Usar una interfaz permite:
 * - Cambiar la implementación (DataStore, Room, etc.) sin modificar el código que la usa
 * - Crear implementaciones de prueba para testing
 * - Aplicar el principio de Inversión de Dependencias
 */
interface SessionManager {

    /**
     * Guarda la sesión del usuario
     *
     * @param username Nombre del usuario autenticado
     */
    suspend fun saveSession(username: String)

    /**
     * Verifica si hay un usuario con sesión activa
     *
     * @return Flow que emite true si hay sesión activa, false en caso contrario
     */
    fun isUserLoggedIn(): Flow<Boolean>

    /**
     * Obtiene el nombre del usuario de la sesión activa
     *
     * @return Flow que emite el username o null si no hay sesión
     */
    fun getUsername(): Flow<String?>

    /**
     * Limpia la sesión del usuario (logout)
     */
    suspend fun clearSession()
}