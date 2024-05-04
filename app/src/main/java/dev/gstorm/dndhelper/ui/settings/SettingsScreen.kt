package dev.gstorm.dndhelper.ui.settings

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.gstorm.dndhelper.Contrast
import dev.gstorm.dndhelper.DarkMode
import dev.gstorm.dndhelper.R
import dev.gstorm.dndhelper.ui.common.DndAppBar
import dev.gstorm.dndhelper.ui.theme.DnDHelperTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { DndAppBar(title = stringResource(R.string.title_settings)) }
    ) { paddingValues ->
        SettingsContent(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues),
            viewModel = viewModel
        )
    }
}

@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val supportsDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val darkModeOptions = remember {
        listOf(
            RadioOption(
                titleRes = R.string.setting_dark_mode_system,
                option = DarkMode.System
            ),
            RadioOption(
                titleRes = R.string.setting_dark_mode_enabled,
                option = DarkMode.Enabled
            ),
            RadioOption(
                titleRes = R.string.setting_dark_mode_disabled,
                option = DarkMode.Disabled
            )
        )
    }
    val contrastOptions = remember {
        listOf(
            RadioOption(
                titleRes = R.string.setting_contrast_default,
                option = Contrast.Default
            ),
            RadioOption(
                titleRes = R.string.setting_contrast_medium,
                option = Contrast.Medium
            ),
            RadioOption(
                titleRes = R.string.setting_contrast_high,
                option = Contrast.High
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (supportsDynamicColor) {
            SettingGroup(
                groupTitle = stringResource(R.string.setting_category_dynamic_color)
            ) {
                SwitchSetting(
                    settingTitle = stringResource(R.string.setting_use_dynamic_color),
                    enabled = uiState.isDynamicColorEnabled
                ) {
                    viewModel.setDynamicColorEnabled(it)
                }
            }
        }

        AnimatedVisibility(
            visible = !supportsDynamicColor || !uiState.isDynamicColorEnabled
        ) {
            SettingGroup(
                groupTitle = stringResource(R.string.setting_category_dark_mode)
            ) {
                RadioSettingGroup(
                    options = darkModeOptions,
                    selectedOption = uiState.darkMode
                ) {
                    viewModel.setDarkMode(it)
                }
            }
        }

        AnimatedVisibility(
            visible = !supportsDynamicColor || !uiState.isDynamicColorEnabled
        ) {
            SettingGroup(
                groupTitle = stringResource(R.string.setting_category_contrast)
            ) {
                RadioSettingGroup(
                    options = contrastOptions,
                    selectedOption = uiState.contrast
                ) {
                    viewModel.setContrast(it)
                }
            }
        }
    }
}

@Composable
private fun SettingGroup(
    modifier: Modifier = Modifier,
    groupTitle: String,
    settingContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = groupTitle,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
        settingContent()
    }
}

@Composable
private fun SwitchSetting(
    modifier: Modifier = Modifier,
    settingTitle: String,
    settingSubtitle: String? = null,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        modifier = modifier
            .clickable { onCheckedChange(!enabled) },
        headlineContent = { Text(text = settingTitle) },
        supportingContent = {
            if (settingSubtitle != null) {
                Text(text = settingSubtitle)
            }
        },
        trailingContent = {
            Switch(
                checked = enabled,
                onCheckedChange = onCheckedChange
            )
        }
    )
}

@Composable
private fun RadioSetting(
    modifier: Modifier = Modifier,
    settingTitle: String,
    selected: Boolean,
    onSelected: () -> Unit
) {
    ListItem(
        modifier = modifier
            .clickable(onClick = onSelected),
        headlineContent = { Text(text = settingTitle) },
        leadingContent = {
            RadioButton(
                selected = selected,
                onClick = onSelected
            )
        }
    )
}

data class RadioOption<T>(
    @StringRes val titleRes: Int,
    val option: T
)

@Composable
private fun <T> RadioSettingGroup(
    modifier: Modifier = Modifier,
    options: List<RadioOption<T>>,
    selectedOption: T,
    onSelected: (T) -> Unit
) {
    Column(
        modifier = modifier
            .selectableGroup()
    ) {
        options.forEach { option ->
            RadioSetting(
                settingTitle = stringResource(id = option.titleRes),
                selected = selectedOption == option.option,
                onSelected = { onSelected(option.option) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioSettingGroupPreview() {
    val options = listOf(
        RadioOption(R.string.setting_dark_mode_enabled, "Option 1"),
        RadioOption(R.string.setting_dark_mode_disabled, "Option 2")
    )
    DnDHelperTheme {
        SettingGroup(groupTitle = "Example Radio Group") {
            RadioSettingGroup(
                options = options,
                selectedOption = "Option 1",
                onSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SwitchSettingPreview() {
    DnDHelperTheme {
        Column {
            SettingGroup(groupTitle = "Example Switch Group") {
                SwitchSetting(
                    settingTitle = "Enabled Switch",
                    enabled = true,
                    onCheckedChange = {}
                )
                SwitchSetting(
                    settingTitle = "Disabled Switch",
                    enabled = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}
