package com.catshome.ClassJournal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.catshome.ClassJournal.com.catshome.classjornal.ClassJournalColors
import com.catshome.ClassJournal.com.catshome.classjornal.ClassJournalStyle
import com.catshome.ClassJournal.com.catshome.classjornal.ClassJournalTheme
import com.catshome.ClassJournal.com.catshome.classjornal.LocalClassJournalColors
import com.catshome.ClassJournal.com.catshome.classjornal.LocalSettingsEventBus
import com.catshome.ClassJournal.com.catshome.classjornal.SettingsEventBus
import com.catshome.classjornal.GroupScreen
import com.catshome.classjornal.MainScreen
import com.catshome.classjornal.NewChildScreen
import com.catshome.classjornal.NewGroupScreen
import com.catshome.classjornal.NewRateScreen
import com.catshome.classjornal.NewVisitScreen
import com.catshome.classjornal.RateScreen
import com.catshome.classjornal.ReportScreen
import com.catshome.classjornal.Screens.ItemScreen
import com.catshome.classjornal.Screens.viewModels.GroupViewModel
import com.catshome.classjornal.ui.theme.MainTheme
import com.catshome.classjornal.ui.theme.baseLightPalette

internal val localNavHost =
    staticCompositionLocalOf<NavHostController> { error("No default implementation") }


@Composable
fun classJournalApp(
    navController: NavHostController

) {
    val viewModel: GroupViewModel =viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ItemScreen.MainScreen.name
    val settingsEventBus = remember { SettingsEventBus() }
    val colorSettings = remember { baseLightPalette }
    val currentSettings by settingsEventBus.currentSettings.collectAsState()
    MainTheme(
        style = currentSettings.style,
        darkTheme = currentSettings.isDarkMode,
        corners = currentSettings.cornerStyle,
        textSize = currentSettings.textSize,
        paddingSize = currentSettings.paddingSize
    ) {
    CompositionLocalProvider(
        localNavHost provides navController,
        LocalSettingsEventBus provides settingsEventBus,
        LocalClassJournalColors provides colorSettings
    ) {
        NavHost(navController, startDestination = ItemScreen.MainScreen.name) {
            composable(route = ItemScreen.MainScreen.name) { MainScreen(navController) }
            composable(route = ItemScreen.GroupScreen.name) { GroupScreen(navController) }
            composable(route = ItemScreen.RateScreen.name) { RateScreen() }
            composable(route = ItemScreen.ReportScreen.name) { ReportScreen() }
            composable(route = ItemScreen.NewGroupScreen.name) { NewGroupScreen(viewModel) }
            composable(route = ItemScreen.NewRateScreen.name) { NewRateScreen() }
            composable(route = ItemScreen.NewChildScreen.name) { NewChildScreen() }
            composable(route = ItemScreen.NewVisitScreen.name) { NewVisitScreen() }
        }
    }

    }
}