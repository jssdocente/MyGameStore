package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.mygamestore.presentation.ui.theme.dimens

/**
 * Componente visual que representa una etiqueta o categoría.
 *
 * @param label Texto de la etiqueta.
 * @param modifier Modificador para el componente.
 * @param onClick Acción al pulsar el chip.
 * @param containerColor Color de fondo del chip.
 */
@Composable
fun DetailChip(
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(MaterialTheme.dimens.buttonCornerRadiusSmall),
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        )
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.buttonSpacing,
                vertical = MaterialTheme.dimens.extraSmall
            ),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Columna que organiza una etiqueta de título y su valor o contenido personalizado.
 *
 * @param label Título de la columna (ej: "DEVELOPER").
 * @param value Valor textual a mostrar.
 * @param modifier Modificador para la columna.
 * @param content Contenido opcional si se requiere algo más que texto (ej: Chips).
 */
@Composable
fun InfoColumn(
    label: String,
    value: String = "",
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.width(180.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraSmall))
        if (content != null) {
            content()
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Badge visual para mostrar la puntuación de Metacritic.
 *
 * @param score Puntuación numérica.
 * @param modifier Modificador para el componente.
 */
@Composable
fun MetacriticBadge(
    score: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xFF00CE7A), // Color verde característico de Metacritic
        shape = RoundedCornerShape(MaterialTheme.dimens.extraSmall),
        modifier = modifier
    ) {
        Text(
            text = score.toString(),
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.extraSmall, vertical = 2.dp),
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}
