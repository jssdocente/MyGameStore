package com.pmdm.mygamestore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.domain.usecase.GameUseCases
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val gameResource: Resource<Game> = Resource.Loading,
    val isFavorite: Boolean = false
)

class DetailViewModel(
    private val useCases: GameUseCases,
    private val gameId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadGame()
    }

    fun loadGame() {
        viewModelScope.launch {
            _uiState.update { it.copy(gameResource = Resource.Loading) }
            val result = useCases.getGameById(gameId)
            _uiState.update { it.copy(gameResource = result) }
        }
    }

    fun toggleFavorite() {
        _uiState.update { it.copy(isFavorite = !it.isFavorite) }
    }
}

class DetailViewModelFactory(
    private val useCases: GameUseCases,
    private val gameId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(useCases, gameId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
