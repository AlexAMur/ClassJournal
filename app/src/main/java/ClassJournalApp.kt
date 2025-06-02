package com.catshome.ClassJournal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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

internal val localNavHost =
    staticCompositionLocalOf<NavHostController> { error("No default implementation") }


@Composable
fun classJournalApp(
    navController: NavHostController

) {
    val viewModel: GroupViewModel =viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ItemScreen.MainScreen.name
    CompositionLocalProvider(
        localNavHost provides navController
    ) {
        NavHost(navController, startDestination = ItemScreen.NewGroupScreen.name) {
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