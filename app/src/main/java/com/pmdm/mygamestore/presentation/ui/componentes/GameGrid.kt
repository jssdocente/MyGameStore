package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.domain.model.Game

/**
 * Componente que muestra una cuadrícula vertical de juegos
 *
 * @param games Lista de juegos a mostrar
 * @param onGameClick Callback cuando se hace click en un juego (recibe el ID)
 * @param modifier Modificador para personalizar el contenedor
 */
@Composable
fun GameGrid(
    games: List<Game>,
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = games,
            key = { game -> game.id }
        ) { game ->
            GameCard(
                game = game,
                onClick = { onGameClick(game.id) }
            )
        }
    }
}
