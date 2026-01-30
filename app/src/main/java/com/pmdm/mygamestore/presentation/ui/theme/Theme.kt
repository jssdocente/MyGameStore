package com.pmdm.mygamestore.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/**
 * CompositionLocal que permite propagar las dimensiones a través del árbol de Compose
 * sin tener que pasarlas manualmente como parámetros en cada función.
 */
private val LocalDimens = staticCompositionLocalOf { Dimens() }

/**
 * Provides a custom set of dimensions to the CompositionLocal hierarchy.
 * This allows child composables to access and use specific dimension configurations
 * such as padding, spacing, or elevation, tailored for the screen size or design requirements.
 *
 * @param dimens A `Dimens` instance defining the dimension values to provide.
 * @param content A composable lambda that will have access to the provided dimensions.
 */
@Composable
fun ProvideDimensions(
    dimens: Dimens,
    content: @Composable () -> Unit) {

    val dimensionSet = remember { dimens }

    // Permite proporcionar las dimensiones a lo largo del arbol de compose.
    CompositionLocalProvider(LocalDimens provides dimensionSet, content = content)
}

/**
 * Función Composable que proporciona el tema principal de la aplicación MyGameStore.
 * Gestiona el esquema de colores (incluyendo colores dinámicos en Android 12+) 
 * y las dimensiones adaptativas según el tamaño de la pantalla.
 *
 * @param darkTheme Indica si se debe usar el tema oscuro. Por defecto usa la configuración del sistema.
 * @param dynamicColor Indica si se deben usar colores dinámicos (disponible en Android 12+).
 * @param content El contenido Composable que será envuelto por este tema.
 */
@Composable
fun MyGameStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Los colores dinámicos están disponibles a partir de Android 12 (S)
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Selección del esquema de colores
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Lógica para diseño adaptativo: seleccionamos dimensiones según el ancho de la pantalla
    // Siguiendo las recomendaciones de Material Design (Window Size Classes)
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenWidthDp = with(density) {
        windowInfo.containerSize.width.toDp()
    }

    val dimens = when {
        screenWidthDp < 600.dp -> CompactDimens
        screenWidthDp < 840.dp -> MediumDimens
        else -> ExpandedDimens
    }

    // CompositionLocalProvider inyecta las dimensiones en el árbol de Compose
    ProvideDimensions(
        dimens = dimens,
        content = {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )
        }
    )
}

/**
 * Propiedad de extensión para MaterialTheme que permite acceder a nuestras dimensiones
 * personalizadas de forma sencilla: MaterialTheme.dimens.paddingMedium
 */
val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current