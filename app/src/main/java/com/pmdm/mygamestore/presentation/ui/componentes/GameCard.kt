package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.Platform

/**
 * Mapea el slug de una plataforma a un icono de Material Design
 */
fun Platform.toIcon(): ImageVector {
    return when {
        slug.contains("pc", ignoreCase = true) -> Icons.Default.Window
        slug.contains("playstation", ignoreCase = true) -> Icons.Default.SportsEsports
        slug.contains("xbox", ignoreCase = true) -> Icons.Default.VideogameAsset
        slug.contains("nintendo", ignoreCase = true) -> Icons.Default.Gamepad
        slug.contains("ios", ignoreCase = true) || slug.contains("android", ignoreCase = true) -> Icons.Default.Smartphone
        else -> Icons.Default.SportsEsports // Fallback
    }
}

/**
 * Fila de iconos de plataformas
 */
@Composable
fun PlatformIconsRow(
    platforms: List<Platform>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Obtenemos iconos únicos para no repetir (ej: PS4 y PS5 usan el mismo icono)
        val uniqueIcons = platforms.map { it.toIcon() }.distinct()
        
        uniqueIcons.forEach { icon ->
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Componente que muestra la información de un juego en formato de tarjeta
 * con un carrusel de imágenes.
 *
 * @param game Datos del juego a mostrar
 * @param onClick Callback cuando se hace click en la tarjeta
 * @param modifier Modificador para personalizar el contenedor
 */
@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp) // Altura reducida para ajustarse al nuevo contenido compacto
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Carrusel de imágenes
            val images = if (game.screenshots.isNotEmpty()) {
                game.screenshots.map { it.image }
            } else {
                listOf(game.imageUrl)
            }

            ImageCarousel(
                images = images,
                contentDescription = game.title,
                height = 180.dp
            )

            // Información del juego
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 1ª Línea: Plataformas y Rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Iconos de Plataforma
                    PlatformIconsRow(platforms = game.platforms)

                    // Rating con estrella
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = game.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                // 2ª Línea: Título (1 línea máx)
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
