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
    val isFavorite: Boolean = false,
    val note: String = "",
    val progressStatus: String = "PENDING"
)

class DetailViewModel(
    private val useCases: GameUseCases,
    private val gameId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadGame()
        checkIfFavorite()
        loadNote()
        addToRecent()
    }

    private fun loadGame() {
        viewModelScope.launch {
            _uiState.update { it.copy(gameResource = Resource.Loading) }
            val result = useCases.getGameById(gameId)
            _uiState.update { it.copy(gameResource = result) }
        }
    }

    private fun checkIfFavorite() {
        viewModelScope.launch {
            val favorite = useCases.isFavorite(gameId)
            _uiState.update { it.copy(isFavorite = favorite) }
        }
    }

    private fun loadNote() {
        viewModelScope.launch {
            useCases.getNoteForGame(gameId).collect { note ->
                _uiState.update { it.copy(note = note ?: "") }
            }
        }
    }

    private fun addToRecent() {
        viewModelScope.launch {
            useCases.addToRecentGames(gameId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val result = useCases.toggleFavorite(gameId)
            if (result is Resource.Success) {
                checkIfFavorite()
            }
        }
    }

    fun saveNote(note: String, status: String) {
        viewModelScope.launch {
            useCases.saveNoteForGame(gameId, note, status)
            _uiState.update { it.copy(note = note, progressStatus = status) }
        }
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
