package com.catshome.ClassJournal


import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.catshome.ClassJournal.Screens.ItemScreen
import com.catshome.ClassJournal.Screens.viewModels.GroupViewModel
import com.catshome.ClassJournal.Screens.viewModels.NewGroupViewModel

internal val localNavHost =
    staticCompositionLocalOf<NavHostController> { error("No default implementation") }



@SuppressLint("SuspiciousIndentation")
@Composable
fun classJournalApp(
    activity: ComponentActivity,
    navController: NavHostController, currentSettings: SettingsBundle
) {
    val viewModel: NewGroupViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ItemScreen.MainScreen.name
    //val settingsEventBus = remember { SettingsEventBus() }
//    val colorSettings = remember { baseLightPalette }
//    val currentSettings by settingsEventBus.currentSettings.collectAsState()
//    MainTheme(
//        style = currentSettings.style,
//        darkTheme = currentSettings.isDarkMode,
//        corners = currentSettings.cornerStyle,
//        textSize = currentSettings.textSize,
//        paddingSize = currentSettings.paddingSize
//    ) {
    NavHost(navController, startDestination = ItemScreen.MainScreen.name) {
        composable(route = ItemScreen.MainScreen.name) {
            MainScreen(navController)
        }
        composable(route = ItemScreen.GroupScreen.name) {
            val viewModel = hiltViewModel<GroupViewModel>()
            GroupScreen(navController, viewModel)
        }
        composable(route = ItemScreen.RateScreen.name) { RateScreen() }
        composable(route = ItemScreen.ReportScreen.name) { ReportScreen() }
        composable(route = ItemScreen.NewGroupScreen.name) { NewGroupScreen(viewModel) }
        composable(route = ItemScreen.NewRateScreen.name) { NewRateScreen() }
        composable(route = ItemScreen.NewChildScreen.name) { NewChildScreen() }
        composable(route = ItemScreen.NewVisitScreen.name) { NewVisitScreen() }
    }
}

