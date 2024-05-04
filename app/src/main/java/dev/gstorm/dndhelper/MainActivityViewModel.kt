package dev.gstorm.dndhelper

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gstorm.dndhelper.domain.settings.ReadSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// This is currently identical to [SettingsUiState], but is meant to remain this size if more
// settings are added to the [SettingsUiState] that are not theming related.
data class MainActivityUiState(
    val isDynamicColorEnabled: Boolean,
    val darkMode: DarkMode,
    val contrast: Contrast
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val readSettingsUseCase: ReadSettingsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MainActivityUiState(
            isDynamicColorEnabled = false,
            darkMode = DarkMode.System,
            contrast = Contrast.Default
        )
    )
    val uiState: StateFlow<MainActivityUiState> = _uiState

    init {
        observeUiState()
    }

    private fun observeUiState() {
        viewModelScope.launch {
            readSettingsUseCase()
                .map {
                    MainActivityUiState(
                        isDynamicColorEnabled = it.isDynamicColorEnabled,
                        darkMode = it.darkMode,
                        contrast = it.contrast
                    )
                }
                .collect {
                    _uiState.value = it
                }
        }
    }
}