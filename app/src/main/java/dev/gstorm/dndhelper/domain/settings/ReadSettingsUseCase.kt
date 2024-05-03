package dev.gstorm.dndhelper.domain.settings

import dev.gstorm.dndhelper.data.settings.SettingsRepository
import javax.inject.Inject

class ReadSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    fun observeDynamicColorEnabled() = repository.dynamicColorsEnabledFlow

    fun observeDarkMode() = repository.darkModeFlow

    fun observeContrast() = repository.contrastFlow
}