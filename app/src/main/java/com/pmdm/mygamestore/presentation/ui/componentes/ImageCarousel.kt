package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pmdm.mygamestore.presentation.ui.theme.dimens

/**
 * Indicadores de página (puntos) para usar con un HorizontalPager.
 */
@Composable
fun PageIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.White.copy(alpha = 0.5f),
    indicatorSize: Dp = MaterialTheme.dimens.small,
    spacing: Dp = MaterialTheme.dimens.extraSmall
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .padding(spacing)
                    .clip(CircleShape)
                    .background(color)
                    .size(indicatorSize)
            )
        }
    }
}

/**
 * Carrusel de imágenes interactivo.
 *
 * @param images Lista de URLs de imágenes.
 * @param modifier Modificador para el carrusel.
 * @param contentDescription Descripción para accesibilidad.
 * @param height Altura del carrusel.
 * @param showIndicators Si se deben mostrar los puntos indicadores.
 */
@Composable
fun ImageCarousel(
    images: List<String>,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    height: Dp = 300.dp,
    showIndicators: Boolean = true
) {
    if (images.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            key = { images[it] }
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (showIndicators && images.size > 1) {
            PageIndicator(
                pagerState = pagerState,
                pageCount = images.size,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = MaterialTheme.dimens.small)
            )
        }
    }
}
