package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.pmdm.mygamestore.presentation.ui.theme.dimens

/**
 * Componente que muestra un texto largo con la opción de expandir/contraer.
 *
 * @param text El texto a mostrar.
 * @param modifier Modificador para el contenedor.
 * @param maxLines Número de líneas visibles cuando está contraído.
 */
@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 4
) {
    var expanded by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(false) }

    Column(modifier = modifier.animateContentSize()) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = if (expanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 22.sp,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow || textLayoutResult.lineCount > maxLines) {
                    isClickable = true
                }
            }
        )
        if (isClickable) {
            Text(
                text = if (expanded) "Show less" else "Show more",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(vertical = MaterialTheme.dimens.extraSmall)
            )
        }
    }
}
