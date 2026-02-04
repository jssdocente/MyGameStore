package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.domain.model.GameCategory

/**
 * Componente que muestra una fila horizontal de chips para filtrar por categoría
 *
 * @param selectedCategory Categoría actualmente seleccionada
 * @param onCategorySelected Callback cuando se selecciona una categoría
 * @param modifier Modificador para personalizar el contenedor
 */
@Composable
fun CategoryChipsRow(
    selectedCategory: GameCategory,
    onCategorySelected: (GameCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(GameCategory.entries) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text(category.name) }
            )
        }
    }
}
