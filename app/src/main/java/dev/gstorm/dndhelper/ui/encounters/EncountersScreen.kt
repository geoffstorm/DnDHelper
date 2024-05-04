package dev.gstorm.dndhelper.ui.encounters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EncountersScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
            text = "Encounters Screen"
        )
    }
}