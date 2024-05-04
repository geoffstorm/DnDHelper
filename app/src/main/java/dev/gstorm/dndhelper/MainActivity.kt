package dev.gstorm.dndhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import dev.gstorm.dndhelper.ui.home.HomeScreen
import dev.gstorm.dndhelper.ui.theme.DnDHelperTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState()
            DnDHelperTheme(
                dynamicColor = uiState.isDynamicColorEnabled,
                darkMode = uiState.darkMode,
                contrast = uiState.contrast
            ) {
                HomeScreen()
            }
        }
    }
}
