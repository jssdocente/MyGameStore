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
    val cardElevation: Dp = 4.dp,

    // Alturas de botones
    val buttonHeightSmall: Dp = 36.dp,      // Botones pequeños/compactos
    val buttonHeightMedium: Dp = 48.dp,     // Altura estándar (recomendada)
    val buttonHeightLarge: Dp = 56.dp,      // Botones destacados

    // Esquinas redondeadas de botones
    val buttonCornerRadius: Dp = 20.dp,     // Para botones estándar
    val buttonCornerRadiusSmall: Dp = 12.dp, // Para botones pequeños

    // Padding interno del botón
    val buttonPaddingHorizontal: Dp = 24.dp,
    val buttonPaddingVertical: Dp = 10.dp,

    // Espaciado entre botones
    val buttonSpacing: Dp = 12.dp
)

/**
 * Dimensiones optimizadas para dispositivos compactos (móviles en vertical).
 */
val CompactDimens = Dimens(
    extraSmall = 4.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
    extraLarge = 32.dp,
    paddingSmall = 8.dp,
    paddingMedium = 16.dp,
    paddingLarge = 24.dp,
    cardElevation = 4.dp,

    // Botones para móviles
    buttonHeightSmall = 36.dp,
    buttonHeightMedium = 48.dp,      // Altura mínima táctil recomendada
    buttonHeightLarge = 56.dp,
    buttonCornerRadius = 20.dp,
    buttonCornerRadiusSmall = 12.dp,
    buttonPaddingHorizontal = 24.dp,
    buttonPaddingVertical = 10.dp,
    buttonSpacing = 12.dp
)

/**
 * Dimensiones optimizadas para dispositivos de tamaño medio (tablets pequeñas o móviles en horizontal).
 */
val MediumDimens = Dimens(
    extraSmall = 6.dp,
    small = 12.dp,
    medium = 20.dp,
    large = 28.dp,
    extraLarge = 40.dp,
    paddingSmall = 12.dp,
    paddingMedium = 24.dp,
    paddingLarge = 36.dp,
    cardElevation = 6.dp,

    // Botones para tablets pequeñas
    buttonHeightSmall = 40.dp,
    buttonHeightMedium = 52.dp,
    buttonHeightLarge = 60.dp,
    buttonCornerRadius = 24.dp,
    buttonCornerRadiusSmall = 14.dp,
    buttonPaddingHorizontal = 28.dp,
    buttonPaddingVertical = 12.dp,
    buttonSpacing = 16.dp
)

/**
 * Dimensiones optimizadas para pantallas expandidas (tablets grandes o escritorio).
 */
val ExpandedDimens = Dimens(
    extraSmall = 8.dp,
    small = 16.dp,
    medium = 24.dp,
    large = 32.dp,
    extraLarge = 48.dp,
    paddingSmall = 16.dp,
    paddingMedium = 32.dp,
    paddingLarge = 48.dp,
    cardElevation = 8.dp,

    // Botones para tablets grandes/escritorio
    // Botones para tablets grandes/escritorio
    buttonHeightSmall = 44.dp,
    buttonHeightMedium = 56.dp,
    buttonHeightLarge = 64.dp,
    buttonCornerRadius = 28.dp,
    buttonCornerRadiusSmall = 16.dp,
    buttonPaddingHorizontal = 32.dp,
    buttonPaddingVertical = 14.dp,
    buttonSpacing = 20.dp
)
