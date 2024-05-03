package dev.gstorm.dndhelper.domain.settings

import dev.gstorm.dndhelper.Contrast
import dev.gstorm.dndhelper.DarkMode
import dev.gstorm.dndhelper.data.settings.SettingsRepository
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend fun setDynamicColorEnabled(enabled: Boolean) {
        repository.setDynamicColorsEnabled(enabled)
    }

    suspend fun setDarkMode(state: DarkMode) {
        repository.setDarkMode(state)
    }

    suspend fun setContrast(state: Contrast) {
        repository.setContrast(state)
    }
}