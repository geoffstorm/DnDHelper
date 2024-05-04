package dev.gstorm.dndhelper.ui.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gstorm.dndhelper.Contrast
import dev.gstorm.dndhelper.DarkMode
import dev.gstorm.dndhelper.domain.settings.ReadSettingsUseCase
import dev.gstorm.dndhelper.domain.settings.SettingsUiState
import dev.gstorm.dndhelper.domain.settings.UpdateSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val readSettingsUseCase: ReadSettingsUseCase,
    private val writeSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingsUiState(
            isDynamicColorEnabled = false,
            darkMode = DarkMode.System,
            contrast = Contrast.Default
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState

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

    private fun observeUiState() {
        viewModelScope.launch {
            readSettingsUseCase().collect {
                _uiState.value = it
            }
        }
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
}