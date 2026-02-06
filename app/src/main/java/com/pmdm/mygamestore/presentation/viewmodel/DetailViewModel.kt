package com.pmdm.mygamestore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.domain.usecase.GameUseCases
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.Resource
import com.pmdm.mygamestore.domain.model.AppError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.pmdm.mygamestore.domain.model.GameProgress

data class DetailUiState(
    val gameResource: Resource<Game> = Resource.Loading,
    val isFavorite: Boolean = false,
    val isInWishlist: Boolean = false,
    val note: String = "",
    val progressStatus: GameProgress = GameProgress.PENDING
)

class DetailViewModel(
    private val useCases: GameUseCases,
    private val gameId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadGame()
        observeFavoriteStatus()
        observeWishlistStatus()
        loadNoteAndProgress()
        addToRecent()
    }

    private fun loadGame() {
        viewModelScope.launch {
            _uiState.update { it.copy(gameResource = Resource.Loading) }
            val result = useCases.getGameById(gameId)
            _uiState.update { it.copy(gameResource = result) }
        }
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            println("[DEBUG_LOG] DetailViewModel: Starting observation of isFavorite for gameId $gameId")
            useCases.isFavorite(gameId).collect { favorite ->
                println("[DEBUG_LOG] DetailViewModel: Received isFavorite emission for gameId $gameId: $favorite")
                _uiState.update { it.copy(isFavorite = favorite) }
            }
        }
    }

    private fun observeWishlistStatus() {
        viewModelScope.launch {
            useCases.isInWishlist(gameId).collect { inWishlist ->
                _uiState.update { it.copy(isInWishlist = inWishlist) }
            }
        }
    }

    private fun loadNoteAndProgress() {
        viewModelScope.launch {
            useCases.getNoteForGame(gameId).collect { note ->
                _uiState.update { it.copy(note = note ?: "") }
            }
        }
        viewModelScope.launch {
            useCases.getProgressForGame(gameId).collect { progress ->
                _uiState.update { it.copy(progressStatus = progress) }
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
            println("[DEBUG_LOG] DetailViewModel: Calling toggleFavorite for gameId $gameId")
            val result = useCases.toggleFavorite(gameId)
            if (result is Resource.Error) {
                val errorMsg = when(val error = result.error) {
                    is AppError.Unknown -> error.message
                    is AppError.DatabaseError -> error.message
                    else -> error.toString()
                }
                println("[DEBUG_LOG] DetailViewModel: toggleFavorite FAILED: $errorMsg")
            } else {
                println("[DEBUG_LOG] DetailViewModel: toggleFavorite call finished successfully")
            }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            useCases.toggleWishlist(gameId)
        }
    }

    fun saveNote(note: String, status: GameProgress) {
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
