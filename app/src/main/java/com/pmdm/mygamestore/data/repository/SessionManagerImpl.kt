package com.pmdm.mygamestore.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pmdm.mygamestore.data.local.dataStorePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 🔧 Implementación de SessionManager usando DataStore
 *
 * @param context Contexto de la aplicación para acceder a DataStore
 */
class SessionManagerImpl(
    private val context: Context
) : SessionManager {

    // Acceso al DataStore a través de la extensión
    private val dataStore = context.dataStorePreferences

    companion object {
        /**
         * Keys tipadas para acceder a las preferencias
         * Usar keys garantiza type-safety y evita errores de tipeo
         */
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USERNAME = stringPreferencesKey("username")
    }

    /**
     * Guarda la sesión del usuario en DataStore
     */
    override suspend fun saveSession(username: String) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[USERNAME] = username
        }
    }

    /**
     * Verifica si el usuario tiene sesión activa
     *
     * @return Flow que emite true si está logueado, false en caso contrario
     */
    override fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    /**
     * Obtiene el nombre del usuario de la sesión activa
     *
     * @return Flow que emite el username o null si no hay sesión
     */
    override fun getUsername(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME]
        }
    }

    /**
     * Limpia toda la sesión del usuario
     */
    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}