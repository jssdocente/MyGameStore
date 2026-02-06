package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pmdm.mygamestore.presentation.ui.theme.dimens

/**
 * Fila adaptativa que organiza elementos (chips) en varias líneas si es necesario.
 *
 * @param items Lista de textos para mostrar en chips.
 * @param modifier Modificador para el contenedor.
 * @param maxLines Máximo de líneas a mostrar.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsFlowRow(
    items: List<String>,
    modifier: Modifier = Modifier,
    maxLines: Int = 2
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
        maxItemsInEachRow = Int.MAX_VALUE,
        maxLines = maxLines
    ) {
        items.forEach { item ->
            DetailChip(item)
        }
    }
}

/**
 * Paginador horizontal para mostrar elementos (chips) en una sola fila con scroll.
 *
 * @param items Lista de textos para mostrar.
 * @param modifier Modificador para el contenedor.
 */
@Composable
fun ChipsPager(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { items.size })
    
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeightMedium),
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium),
        pageSpacing = MaterialTheme.dimens.small,
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        DetailChip(items[page])
    }
}
