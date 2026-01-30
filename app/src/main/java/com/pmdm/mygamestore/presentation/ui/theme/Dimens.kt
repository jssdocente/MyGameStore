package com.pmdm.mygamestore.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Clase de datos que define las dimensiones estándar utilizadas en la aplicación.
 * Permite centralizar los tamaños de espaciado, elevación, etc., facilitando cambios globales
 * y permitiendo diferentes valores según el tamaño de la pantalla.
 */
data class Dimens(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,
    val cardElevation: Dp = 4.dp
)

/**
 * Dimensiones optimizadas para dispositivos compactos (móviles en vertical).
 */
val CompactDimens = Dimens(
    paddingSmall = 8.dp,
    paddingMedium = 16.dp,
    paddingLarge = 24.dp
)

/**
 * Dimensiones optimizadas para dispositivos de tamaño medio (tablets pequeñas o móviles en horizontal).
 */
val MediumDimens = Dimens(
    paddingSmall = 12.dp,
    paddingMedium = 24.dp,
    paddingLarge = 36.dp
)

/**
 * Dimensiones optimizadas para pantallas expandidas (tablets grandes o escritorio).
 */
val ExpandedDimens = Dimens(
    paddingSmall = 16.dp,
    paddingMedium = 32.dp,
    paddingLarge = 48.dp
)
