package dev.gstorm.dndhelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.gstorm.dndhelper.ui.characterse.CharactersScreen
import dev.gstorm.dndhelper.ui.encounters.EncountersScreen
import dev.gstorm.dndhelper.ui.settings.SettingsScreen

internal sealed class RootScreen(val route: String) {
    object Characters : RootScreen(route = "characters")
    object Encounters : RootScreen(route = "encounters")
    object Settings : RootScreen(route = "settings")
}

private sealed class Screen(private val route: String) {
    fun createRoute(root: RootScreen) = "${root.route}/$route"

    data object Characters : Screen(route = "characters")
    data object Encounters : Screen(route = "encounters")
    data object Settings : Screen(route = "settings")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = RootScreen.Characters.route
    ) {
        addCharactersTopLevel(navController)
        addEncountersTopLevel(navController)
        addSettingsTopLevel(navController)
    }
}

private fun NavGraphBuilder.addCharactersTopLevel(
    navController: NavController,
    rootScreen: RootScreen = RootScreen.Characters
) {
    navigation(
        route = rootScreen.route,
        startDestination = Screen.Characters.createRoute(rootScreen)
    ) {
        addCharactersScreen(navController, rootScreen)
    }
}

private fun NavGraphBuilder.addEncountersTopLevel(
    navController: NavController,
    rootScreen: RootScreen = RootScreen.Encounters
) {
    navigation(
        route = rootScreen.route,
        startDestination = Screen.Encounters.createRoute(rootScreen)
    ) {
        addEncountersScreen(navController, rootScreen)
    }
}

private fun NavGraphBuilder.addSettingsTopLevel(
    navController: NavController,
    rootScreen: RootScreen = RootScreen.Settings
) {
    navigation(
        route = rootScreen.route,
        startDestination = Screen.Settings.createRoute(rootScreen)
    ) {
        addSettingsScreen(navController, rootScreen)
    }
}

private fun NavGraphBuilder.addCharactersScreen(
    navController: NavController,
    root: RootScreen
) {
    composable(
        route = Screen.Characters.createRoute(root)
    ) {
        CharactersScreen()
    }
}

private fun NavGraphBuilder.addEncountersScreen(
    navController: NavController,
    root: RootScreen
) {
    composable(
        route = Screen.Encounters.createRoute(root)
    ) {
        EncountersScreen()
    }
}

private fun NavGraphBuilder.addSettingsScreen(
    navController: NavController,
    root: RootScreen
) {
    composable(
        route = Screen.Settings.createRoute(root)
    ) {
        SettingsScreen()
    }
}