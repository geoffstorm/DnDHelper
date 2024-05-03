package dev.gstorm.dndhelper.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.gstorm.dndhelper.AppSettings
import dev.gstorm.dndhelper.Contrast
import dev.gstorm.dndhelper.DarkMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore: DataStore<AppSettings> by dataStore(
    fileName = "settings.proto",
    serializer = SettingsSerializer
)

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val dynamicColorsEnabledFlow: Flow<Boolean> = context.settingsDataStore.data
        .map { settings -> settings.dynamicColorEnabled }

    val darkModeFlow: Flow<DarkMode> = context.settingsDataStore.data
        .map { settings -> settings.darkMode }

    val contrastFlow: Flow<Contrast> = context.settingsDataStore.data
        .map { settings -> settings.contrast }

    suspend fun setDynamicColorsEnabled(enabled: Boolean) {
        context.settingsDataStore.updateData { settings ->
            settings.toBuilder()
                .setDynamicColorEnabled(enabled)
                .build()
        }
    }

    suspend fun setDarkMode(darkMode: DarkMode) {
        context.settingsDataStore.updateData { settings ->
            settings.toBuilder()
                .setDarkMode(darkMode)
                .build()
        }
    }

    suspend fun setContrast(contrast: Contrast) {
        context.settingsDataStore.updateData { settings ->
            settings.toBuilder()
                .setContrast(contrast)
                .build()
        }
    }
}