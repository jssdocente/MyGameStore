package com.pmdm.mygamestore.presentation.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.PlatformEnum
import kotlinx.coroutines.launch

/**
 * Enumeración para controlar qué filtro se está editando en el BottomSheet
 */
private enum class FilterType {
    NONE, CATEGORY, PLATFORM, INTERVAL
}

/**
 * Componente que muestra el sistema de filtros compacto en una sola fila
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSystem(
    selectedCategory: GameCategory,
    onCategorySelected: (GameCategory) -> Unit,
    selectedPlatform: PlatformEnum,
    onPlatformSelected: (PlatformEnum) -> Unit,
    selectedInterval: DateInterval,
    onIntervalSelected: (DateInterval) -> Unit,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true
) {
    var showSheet by remember { mutableStateOf(false) }
    var activeFilterType by remember { mutableStateOf(FilterType.NONE) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val hasActiveFilters = selectedCategory != GameCategory.ALL ||
            selectedPlatform != PlatformEnum.ALL ||
            selectedInterval != DateInterval.ALL_TIME

    Column(modifier = modifier) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = if (showIcon) 16.dp else 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono indicador de filtros
            if (showIcon) {
                item {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filtros",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Chip de Categoría
            item {
                CompactFilterChip(
                    label = if (selectedCategory == GameCategory.ALL) "Categoría" else selectedCategory.name.lowercase()
                        .replaceFirstChar { it.uppercase() },
                    isSelected = selectedCategory != GameCategory.ALL,
                    onClick = {
                        activeFilterType = FilterType.CATEGORY
                        showSheet = true
                    }
                )
            }

            // Chip de Plataforma
            item {
                CompactFilterChip(
                    label = if (selectedPlatform == PlatformEnum.ALL) "Plataforma" else selectedPlatform.name.lowercase()
                        .replaceFirstChar { it.uppercase() },
                    isSelected = selectedPlatform != PlatformEnum.ALL,
                    onClick = {
                        activeFilterType = FilterType.PLATFORM
                        showSheet = true
                    }
                )
            }

            // Chip de Fecha
            item {
                CompactFilterChip(
                    label = when (selectedInterval) {
                        DateInterval.ALL_TIME -> "Fecha"
                        DateInterval.LAST_WEEK -> "Semana"
                        DateInterval.LAST_30_DAYS -> "30 días"
                        DateInterval.LAST_90_DAYS -> "90 días"
                    },
                    isSelected = selectedInterval != DateInterval.ALL_TIME,
                    onClick = {
                        activeFilterType = FilterType.INTERVAL
                        showSheet = true
                    }
                )
            }

            // Botón para limpiar todo (solo si hay filtros)
            if (hasActiveFilters) {
                item {
                    IconButton(
                        onClick = {
                            onCategorySelected(GameCategory.ALL)
                            onPlatformSelected(PlatformEnum.ALL)
                            onIntervalSelected(DateInterval.ALL_TIME)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Limpiar filtros",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    // Bottom Sheet para seleccionar opciones
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                activeFilterType = FilterType.NONE
            },
            sheetState = sheetState
        ) {
            FilterOptionsList(
                filterType = activeFilterType,
                selectedCategory = selectedCategory,
                selectedPlatform = selectedPlatform,
                selectedInterval = selectedInterval,
                onCategorySelected = {
                    onCategorySelected(it)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        showSheet = false
                    }
                },
                onPlatformSelected = {
                    onPlatformSelected(it)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        showSheet = false
                    }
                },
                onIntervalSelected = {
                    onIntervalSelected(it)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        showSheet = false
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        trailingIcon = {
            Icon(
                imageVector = if (isSelected) Icons.Default.Check else Icons.Default.Category,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
        }
    )
}

@Composable
private fun FilterOptionsList(
    filterType: FilterType,
    selectedCategory: GameCategory,
    selectedPlatform: PlatformEnum,
    selectedInterval: DateInterval,
    onCategorySelected: (GameCategory) -> Unit,
    onPlatformSelected: (PlatformEnum) -> Unit,
    onIntervalSelected: (DateInterval) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val title = when (filterType) {
            FilterType.CATEGORY -> "Seleccionar Categoría"
            FilterType.PLATFORM -> "Seleccionar Plataforma"
            FilterType.INTERVAL -> "Seleccionar Fecha"
            else -> ""
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        HorizontalDivider()

        LazyColumn {
            when (filterType) {
                FilterType.CATEGORY -> {
                    items(GameCategory.entries) { category ->
                        FilterOptionItem(
                            label = category.name.lowercase().replaceFirstChar { it.uppercase() },
                            isSelected = category == selectedCategory,
                            onClick = { onCategorySelected(category) }
                        )
                    }
                }

                FilterType.PLATFORM -> {
                    items(PlatformEnum.entries) { platform ->
                        FilterOptionItem(
                            label = platform.name.lowercase().replaceFirstChar { it.uppercase() },
                            isSelected = platform == selectedPlatform,
                            onClick = { onPlatformSelected(platform) }
                        )
                    }
                }

                FilterType.INTERVAL -> {
                    items(DateInterval.entries) { interval ->
                        val label = when (interval) {
                            DateInterval.ALL_TIME -> "Todo el tiempo"
                            DateInterval.LAST_WEEK -> "Última semana"
                            DateInterval.LAST_30_DAYS -> "Últimos 30 días"
                            DateInterval.LAST_90_DAYS -> "Últimos 90 días"
                        }
                        FilterOptionItem(
                            label = label,
                            isSelected = interval == selectedInterval,
                            onClick = { onIntervalSelected(interval) }
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun FilterOptionItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

/**
 * Mantenemos este por compatibilidad temporal si se usa en otros sitios.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChipsRow(
    selectedCategory: GameCategory,
    onCategorySelected: (GameCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    CompactFilterChip(
        label = selectedCategory.name,
        isSelected = selectedCategory != GameCategory.ALL,
        onClick = { showSheet = true }
    )

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false }, sheetState = sheetState) {
            FilterOptionsList(
                filterType = FilterType.CATEGORY,
                selectedCategory = selectedCategory,
                selectedPlatform = PlatformEnum.ALL,
                selectedInterval = DateInterval.ALL_TIME,
                onCategorySelected = {
                    onCategorySelected(it)
                    scope.launch { sheetState.hide() }.invokeOnCompletion { showSheet = false }
                },
                onPlatformSelected = {},
                onIntervalSelected = {}
            )
        }
    }
}
