package com.catshome.ClassJournal

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
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
import com.catshome.ClassJournal.com.catshome.classjornal.LocalSettingsEventBus
import com.catshome.ClassJournal.com.catshome.classjornal.SettingsBundle
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

internal val localNavHost =
    staticCompositionLocalOf<NavHostController> { error("No default implementation") }


@SuppressLint("SuspiciousIndentation")
@Composable
fun classJournalApp(activity: ComponentActivity,
    navController: NavHostController, currentSettings: SettingsBundle
) {
    val viewModel: GroupViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ItemScreen.MainScreen.name
  //  val settingsEventBus = remember { SettingsEventBus() }
//    val colorSettings = remember { baseLightPalette }
//    val currentSettings by settingsEventBus.currentSettings.collectAsState()
    MainTheme(
        style = currentSettings.style,
        darkTheme = currentSettings.isDarkMode,
        corners = currentSettings.cornerStyle,
        textSize = currentSettings.textSize,
        paddingSize = currentSettings.paddingSize
    ) {
//        CompositionLocalProvider(
//            LocalSettingsEventBus provides settingsEventBus
//        ) {
        NavHost(navController, startDestination = ItemScreen.MainScreen.name) {
            composable(route = ItemScreen.MainScreen.name) {
                MainScreen(
                    navController,
                    //currentSettings
                )
            }
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
//}
