package dev.gstorm.dndhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
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
            }
        }
    }
}
