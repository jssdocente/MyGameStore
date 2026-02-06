package com.pmdm.mygamestore.data.repository

import com.pmdm.mygamestore.MyGameStoreApp
import com.pmdm.mygamestore.data.local.entities.UserEntity
import kotlinx.coroutines.delay

/**
 * 🔧 Implementación local del repositorio de autenticación con persistencia en Room
 *
 * Esta implementación valida credenciales contra una lista local y persiste
 * el perfil del usuario en Room al registrarse o hacer login.
 */
class AuthRepositoryImpl : AuthRepository {

    private val db = MyGameStoreApp.database

    /**
     * Usuarios válidos para login (simulación local)
     * En un escenario real, esto vendría de una API o base de datos
     */
    private val validUsers = mutableMapOf(
        "admin" to "1234",
        "user" to "password"
    )

    /**
     * Base de datos simulada de usuarios registrados
     */
    private val registeredUsers = mutableMapOf<String, UserData>(
        "admin" to UserData("admin", "admin@example.com", "1234"),
        "user" to UserData("user", "user@example.com", "password")
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
            // Persistir perfil básico en Room si no existe
            val userData = registeredUsers[username]
            if (userData != null) {
                db.userDao().insertUser(
                    UserEntity(
                        username = userData.username,
                        name = userData.username.replaceFirstChar { it.uppercase() },
                        email = userData.email
                    )
                )
            }
            LoginResult.Success(username = username)
        } else {
            LoginResult.Error(message = "Invalid username or password")
        }
    }

    /**
     * Registra un nuevo usuario
     * 
     * @param username Nombre de usuario
     * @param email Email del usuario
     * @param password Contraseña
     * @return RegisterResult indicando éxito o error
     */
    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): RegisterResult {
        delay(1500)

        // Verificar si el usuario ya existe
        if (registeredUsers.containsKey(username)) {
            return RegisterResult.Error("Username already exists")
        }

        // Verificar si el email ya está registrado
        if (registeredUsers.values.any { it.email == email }) {
            return RegisterResult.Error("Email already registered")
        }

        // Registrar nuevo usuario en el mapa (simulado)
        registeredUsers[username] = UserData(username, email, password)
        validUsers[username] = password

        // Persistir en Room (real)
        db.userDao().insertUser(
            UserEntity(
                username = username,
                name = username.replaceFirstChar { it.uppercase() },
                email = email
            )
        )

        return RegisterResult.Success(username = username)
    }
}

/**
 * Clase de datos para almacenar información del usuario
 */
data class UserData(
    val username: String,
    val email: String,
    val password: String
)