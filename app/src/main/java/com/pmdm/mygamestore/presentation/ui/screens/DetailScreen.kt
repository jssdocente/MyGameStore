package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pmdm.mygamestore.data.repository.MockGamesRepositoryImpl
import com.pmdm.mygamestore.domain.model.AppError
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.Resource
import com.pmdm.mygamestore.domain.usecase.GameUseCases
import com.pmdm.mygamestore.presentation.ui.componentes.*
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import com.pmdm.mygamestore.presentation.viewmodel.DetailViewModel
import com.pmdm.mygamestore.presentation.viewmodel.DetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    gameId: Int,
    onBack: () -> Unit = {},
    viewModel: DetailViewModel = viewModel(
        key = "DetailViewModel_$gameId",
        factory = DetailViewModelFactory(
            GameUseCases(MockGamesRepositoryImpl()),
            gameId
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(bottom = padding.calculateBottomPadding())) {
            when (val resource = uiState.gameResource) {
                is Resource.Loading -> LoadingIndicator()
                is Resource.Error -> ErrorMessage(
                    message = when (resource.error) {
                        is AppError.NotFound -> "Game not found"
                        is AppError.NetworkError -> resource.error.message
                        else -> "An unexpected error occurred"
                    },
                    onRetry = { viewModel.loadGame() }
                )
                is Resource.Success -> {
                    DetailContent(
                        game = resource.data,
                        isFavorite = uiState.isFavorite,
                        onToggleFavorite = { viewModel.toggleFavorite() }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    game: Game,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = MaterialTheme.dimens.medium)
    ) {
        item {
            // Carrusel de imágenes (sin padding para ocupar todo el ancho)
            val images = if (game.screenshots.isNotEmpty()) {
                game.screenshots.map { it.image }
            } else {
                listOf(game.imageUrl)
            }

            ImageCarousel(
                images = images,
                contentDescription = game.title
            )
        }

        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .size(MaterialTheme.dimens.buttonHeightLarge)
                        .clip(RoundedCornerShape(MaterialTheme.dimens.medium))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.medium, vertical = MaterialTheme.dimens.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(MaterialTheme.dimens.medium)
                )
                Text(
                    text = " ${game.rating}/5",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFFFC107),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.buttonSpacing))
                game.metacritic?.let { score ->
                    MetacriticBadge(score = score)
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
                    Text(
                        text = "Metascore",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoColumn("DEVELOPER", game.developers.firstOrNull()?.name ?: "N/A")
                InfoColumn("GENRE", "") {
                    Row {
                        game.genres.take(2).forEach { genre ->
                            DetailChip(genre.name)
                            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoColumn("RELEASE DATE", game.releaseDate)
                InfoColumn("PUBLISHER", game.publishers.firstOrNull()?.name ?: "N/A")
            }
        }

        item {
            Column(modifier = Modifier.padding(MaterialTheme.dimens.medium)) {
                Text(
                    "TAGS",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
                TagsFlowRow(game.tags.map { it.name })
            }
        }

        item {
            Column(modifier = Modifier.padding(MaterialTheme.dimens.medium)) {
                Text(
                    "ABOUT",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
                ExpandableText(text = game.description)
            }
        }

        item {
            Column(modifier = Modifier.padding(vertical = MaterialTheme.dimens.medium)) {
                Text(
                    "PLATFORMS",
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
                ChipsPager(game.platforms.map { it.name })
            }
        }

        if (game.movies.isNotEmpty()) {
            item {
                RoundedButton(
                    texto = "WATCH TRAILER",
                    onClick = { /* Play Trailer */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.medium)
                        .height(MaterialTheme.dimens.buttonHeightLarge),
                    shape = RoundedCornerShape(MaterialTheme.dimens.medium)
                )
            }
        }
    }
}
