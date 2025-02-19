package dev.gstorm.dndhelper.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndAppBar(
    modifier: Modifier = Modifier,
    title: String
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) }
    )
}