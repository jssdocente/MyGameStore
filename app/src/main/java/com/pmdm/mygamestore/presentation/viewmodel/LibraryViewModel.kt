package com.pmdm.mygamestore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.Resource
import com.pmdm.mygamestore.domain.usecase.GameUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.pmdm.mygamestore.domain.model.LibraryStatus

data class LibraryUiState(
    val libraryGames: Resource<List<Game>> = Resource.Loading,
    val selectedFilter: LibraryStatus = LibraryStatus.ALL
)

class LibraryViewModel(
    private val useCases: GameUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        loadLibrary()
    }

    fun loadLibrary() {
        viewModelScope.launch {
            _uiState.update { it.copy(libraryGames = Resource.Loading) }
            useCases.getLibraryGames(_uiState.value.selectedFilter).collect { resource ->
                _uiState.update { it.copy(libraryGames = resource) }
            }
        }
    }

    fun onFilterSelected(status: LibraryStatus) {
        if (_uiState.value.selectedFilter == status) return
        _uiState.update { it.copy(selectedFilter = status) }
        loadLibrary()
    }

    fun removeFromLibrary(gameId: Int) {
        viewModelScope.launch {
            useCases.removeFromLibrary(gameId)
            // No es necesario llamar a loadLibrary() si usamos Flow reactivo desde Room,
            // pero como getLibraryGames() emite el Flow de Room, se actualizará solo.
        }
    }
}

class LibraryViewModelFactory(
    private val useCases: GameUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(useCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
