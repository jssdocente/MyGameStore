package com.pmdm.mygamestore.data.repository

/**
 * 📋 Interfaz que define las operaciones de autenticación
 *
 * Usar una interfaz permite:
 * - Cambiar la implementación sin modificar el ViewModel
 * - Crear implementaciones de prueba para testing
 * - Aplicar el principio de Inversión de Dependencias
 */
interface AuthRepository {
    /**
     * Intenta autenticar a un usuario
     *
     * @param username Nombre de usuario
     * @param password Contraseña
     * @return LoginResult indicando éxito o error
     */
    suspend fun login(username: String, password: String): LoginResult

    /**
     * Registra un nuevo usuario
     * 
     * @param username Nombre de usuario
     * @param email Email del usuario
     * @param password Contraseña
     * @return RegisterResult indicando éxito o error
     */
    suspend fun register(
        username: String, 
        email: String, 
        password: String
    ): RegisterResult
}