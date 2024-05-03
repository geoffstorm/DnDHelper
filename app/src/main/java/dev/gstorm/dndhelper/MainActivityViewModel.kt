package dev.gstorm.dndhelper

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gstorm.dndhelper.domain.settings.ReadSettingsUseCase
import dev.gstorm.dndhelper.domain.settings.UpdateSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainActivityUiState(
    val isDynamicColorEnabled: Boolean,
    val darkMode: DarkMode,
    val contrast: Contrast
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val readSettingsUseCase: ReadSettingsUseCase,
    private val writeSettingsUseCase: UpdateSettingsUseCase  // TODO testing only
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

    fun toggleDynamicColor() {
        setDynamicColorEnabled(!_uiState.value.isDynamicColorEnabled)
    }

    fun cycleDarkMode() {
        val current = _uiState.value.darkMode
        val index = current.number
        val next = (index + 1) % 3
        setDarkMode(DarkMode.forNumber(next))
    }

    fun cycleContrast() {
        val current = _uiState.value.contrast
        val index = current.number
        val next = (index + 1) % 3
        setContrast(Contrast.forNumber(next))
    }

    private fun setDynamicColorEnabled(enabled: Boolean) {
        viewModelScope.launch {
            writeSettingsUseCase.setDynamicColorEnabled(enabled)
        }
    }

    private fun setDarkMode(darkMode: DarkMode) {
        viewModelScope.launch {
            writeSettingsUseCase.setDarkMode(darkMode)
        }
    }

    private fun setContrast(contrast: Contrast) {
        viewModelScope.launch {
            writeSettingsUseCase.setContrast(contrast)
        }
    }

    private fun observeUiState() {
        viewModelScope.launch {
            combine(
                readSettingsUseCase.observeDynamicColorEnabled(),
                readSettingsUseCase.observeDarkMode(),
                readSettingsUseCase.observeContrast()
            ) { isDynamicColorEnabled, darkMode, contrast ->
                MainActivityUiState(isDynamicColorEnabled, darkMode, contrast)
            }.collect {
                _uiState.value = it
            }
        }
    }
}