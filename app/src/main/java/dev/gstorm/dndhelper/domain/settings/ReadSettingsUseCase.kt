package dev.gstorm.dndhelper.domain.settings

import dev.gstorm.dndhelper.Contrast
import dev.gstorm.dndhelper.DarkMode
import dev.gstorm.dndhelper.data.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class SettingsUiState(
    val isDynamicColorEnabled: Boolean,
    val darkMode: DarkMode,
    val contrast: Contrast
)

class ReadSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<SettingsUiState> = combine(
        observeDynamicColorEnabled(),
        observeDarkMode(),
        observeContrast()
    ) { isDynamicColorEnabled, darkMode, contrast ->
        SettingsUiState(
            isDynamicColorEnabled = isDynamicColorEnabled,
            darkMode = darkMode,
            contrast = contrast
        )
    }

    private fun observeDynamicColorEnabled() = repository.dynamicColorsEnabledFlow

    private fun observeDarkMode() = repository.darkModeFlow

    private fun observeContrast() = repository.contrastFlow
}