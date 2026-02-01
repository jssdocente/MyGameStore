package com.pmdm.mygamestore.data.repository

/**
 * 🎯 Sealed class que representa los posibles resultados de un login
 *
 * Una sealed class permite:
 * - Definir un conjunto cerrado y conocido de posibilidades
 * - Usar when exhaustivo (el compilador verifica que cubrimos todos los casos)
 * - Evitar errores con null o excepciones no controladas
 */
sealed class LoginResult {
    /**
     * Login exitoso
     * @param username Nombre del usuario autenticado
     */
    data class Success(val username: String) : LoginResult()

    /**
     * Login fallido
     * @param message Mensaje describiendo el error
     */
    data class Error(val message: String) : LoginResult()
}

/**
 * 🎯 Sealed class que representa los posibles resultados de un registro
 */
sealed class RegisterResult {
    /**
     * Registro exitoso
     * @param username Nombre del usuario registrado
     */
    data class Success(val username: String) : RegisterResult()

    /**
     * Registro fallido
     * @param message Mensaje describiendo el error
     */
    data class Error(val message: String) : RegisterResult()
}
