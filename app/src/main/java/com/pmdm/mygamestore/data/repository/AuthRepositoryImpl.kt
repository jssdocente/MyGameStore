package com.pmdm.mygamestore.data.repository

import kotlinx.coroutines.delay

/**
 * 🔧 Implementación local del repositorio de autenticación
 *
 * Esta implementación valida credenciales contra una lista local.
 * Simula el comportamiento de una fuente de datos real con delay.
 */
class AuthRepositoryImpl : AuthRepository {

    /**
     * Usuarios válidos para login (simulación local)
     * En un escenario real, esto vendría de una API o base de datos
     */
    private val validUsers = mapOf(
        "admin" to "1234",
        "user" to "password"
    )

    /**
     * Valida las credenciales del usuario
     *
     * @param username Nombre de usuario
     * @param password Contraseña
     * @return LoginResult.Success si las credenciales son correctas,
     *         LoginResult.Error en caso contrario
     */
    override suspend fun login(username: String, password: String): LoginResult {
        // Simula el tiempo que tomaría una operación real
        // (consulta a API, lectura de base de datos, etc.)
        delay(1500)

        // Validar credenciales
        return if (validUsers[username] == password) {
            LoginResult.Success(username = username)
        } else {
            LoginResult.Error(message = "Invalid username or password")
        }
    }
}