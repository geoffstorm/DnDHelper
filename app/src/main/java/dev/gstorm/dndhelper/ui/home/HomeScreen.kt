package dev.gstorm.dndhelper.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import dev.gstorm.dndhelper.R
import dev.gstorm.dndhelper.ui.navigation.AppNavigation
import dev.gstorm.dndhelper.ui.navigation.RootScreen

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    // GS: topBar should always be provided by the individual screens, not the root
    Scaffold(
        bottomBar = {
            val currentSelectedItem by navController.currentScreenAsState()
            HomeBottomBar(
                modifier = Modifier.fillMaxWidth(),
                currentSelectedItem
            ) { root ->
                navController.navigate(root.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            AppNavigation(navController = navController)
        }
    }
}

@Composable
private fun HomeBottomBar(
    modifier: Modifier = Modifier,
    selectedNavigation: RootScreen,
    onNavigationSelected: (RootScreen) -> Unit = {}
) {
    NavigationBar(modifier = modifier) {
        homeNavigationBarItems.forEach { item ->
            NavigationBarItem(
                selected = selectedNavigation == item.screen,
                onClick = { onNavigationSelected(item.screen) },
                icon = { Icon(item.icon, contentDescription = stringResource(id = item.titleResId)) },
                label = { Text(stringResource(item.titleResId)) }
            )
        }
    }
}

private data class HomeNavigationBarItem(
    val screen: RootScreen,
    val icon: ImageVector,
    @StringRes val titleResId: Int
)

private val homeNavigationBarItems = listOf(
    HomeNavigationBarItem(
        screen = RootScreen.Characters,
        icon = Icons.Default.Person,
        titleResId = R.string.nav_title_characters
    ),
    HomeNavigationBarItem(
        screen = RootScreen.Encounters,
        icon = Icons.Default.Groups,
        titleResId = R.string.nav_title_encounters
    ),
    HomeNavigationBarItem(
        screen = RootScreen.Settings,
        icon = Icons.Default.Settings,
        titleResId = R.string.nav_title_settings
    )
)

@Stable
@Composable
private fun NavController.currentScreenAsState(): State<RootScreen> {
    val selectedItem = remember { mutableStateOf<RootScreen>(RootScreen.Characters) }
    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == RootScreen.Characters.route } -> {
                    selectedItem.value = RootScreen.Characters
                }
                destination.hierarchy.any { it.route == RootScreen.Encounters.route } -> {
                    selectedItem.value = RootScreen.Encounters
                }
                destination.hierarchy.any { it.route == RootScreen.Settings.route } -> {
                    selectedItem.value = RootScreen.Settings
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}