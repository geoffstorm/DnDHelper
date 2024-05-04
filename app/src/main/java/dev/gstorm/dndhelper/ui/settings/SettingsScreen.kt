package dev.gstorm.dndhelper.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.toggleDynamicColor() }
        ) {
            Text(text = "Dynamic Color")
        }
        Button(
            onClick = { viewModel.cycleDarkMode() }
        ) {
            Text(text = "Dark Mode")
        }
        Button(
            onClick = { viewModel.cycleContrast() }
        ) {
            Text(text = "Contrast")
        }
    }
}