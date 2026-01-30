package com.pmdm.mygamestore.presentation.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppRoutes: NavKey {
    @Serializable
    data object Splash : AppRoutes
    @Serializable
    data object Login : AppRoutes
    @Serializable
    data object Register : AppRoutes
    @Serializable
    data object Home : AppRoutes
    @Serializable
    data object Library : AppRoutes
    @Serializable
    data object Profile : AppRoutes
    @Serializable
    data class Detail(val gameId: Int) : AppRoutes
}
