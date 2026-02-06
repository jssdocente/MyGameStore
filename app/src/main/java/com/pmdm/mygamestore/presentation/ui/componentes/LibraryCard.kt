package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.LibraryStatus

/**
 * Tarjeta de juego para la biblioteca con soporte para Swipe-to-Dismiss.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryGameCard(
    game: Game,
    status: LibraryStatus,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
            }
            false // Siempre devolvemos false para que no se "desvanezca" la tarjeta hasta que confirmemos la eliminación real
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }, label = "background"
            )
            val scale by animateFloatAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f,
                label = "scale"
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.scale(scale),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    ) {
        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .height(110.dp)
            ) {
                // Imagen Portada
                AsyncImage(
                    model = game.imageUrl,
                    contentDescription = game.title,
                    modifier = Modifier
                        .width(110.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Información
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 1ª Fila: Plataformas y Badge de Estado
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PlatformIconsRow(platforms = game.platforms)
                        
                        // Badge de Estado
                        if (status != LibraryStatus.NONE && status != LibraryStatus.ALL) {
                            DetailChip(
                                label = status.displayName,
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                    
                    // 2ª Fila: Título
                    Text(
                        text = game.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    // 3ª Fila: Rating
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

                    // 4ª Fila: Géneros (Última línea)
                    GenreChipsRow(genres = game.genres.map { it.name })
                }
            }
        }
    }
}
