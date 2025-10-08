package com.catshome.classJournal


import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.catshome.classJournal.navigate.DetailsChild
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.PayListViewModel
import com.catshome.classJournal.screens.PayList.PlayListScreen
import com.catshome.classJournal.screens.Visit.VisitListViewModel
import com.catshome.classJournal.screens.Visit.visitListScreen
import com.catshome.classJournal.screens.child.ChildListViewModel
import com.catshome.classJournal.screens.child.NewChildScreen
import com.catshome.classJournal.screens.child.NewChildViewModel
import com.catshome.classJournal.screens.group.GroupScreen
import com.catshome.classJournal.screens.group.NewGroupScreen
import com.catshome.classJournal.screens.viewModels.GroupViewModel
import com.catshome.classJournal.screens.viewModels.NewGroupViewModel

internal val localNavHost =
    staticCompositionLocalOf<NavHostController> { error("No default implementation") }


@SuppressLint("SuspiciousIndentation")
@Composable
fun classJournalApp(
    activity: ComponentActivity,
    navController: NavHostController,
    currentSettings: SettingsBundle
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ItemScreen.PayListScreen.name

    NavHost(navController, startDestination = ItemScreen.PayListScreen.name) {
        composable(route = ItemScreen.PayListScreen.name) {
            val viewModel = hiltViewModel<PayListViewModel>()
            PlayListScreen(navController, viewModel)

        }
        composable(route = ItemScreen.VisitListScreen.name) {
            val viewModel = hiltViewModel<VisitListViewModel>()
            visitListScreen(navController, viewModel)

        }
        composable(route = ItemScreen.MainScreen.name) {
            val viewModel = hiltViewModel<ChildListViewModel>()
            ChildListScreen(navController, viewModel)
        }
        composable(route = ItemScreen.GroupScreen.name) {
            val viewModel = hiltViewModel<GroupViewModel>()
            GroupScreen(navController, viewModel)
        }
        composable(route = ItemScreen.RateScreen.name) { RateScreen() }
        composable(route = ItemScreen.ReportScreen.name) { ReportScreen() }
        composable<DetailsGroup> { backStackEntry ->
            val idGroup = backStackEntry.toRoute<DetailsGroup>()
            val viewModel = hiltViewModel<NewGroupViewModel>()
            NewGroupScreen(viewModel = viewModel, idGroup = idGroup.GroupID)
        }
        composable(route = ItemScreen.NewGroupScreen.name) {
            val viewModel = hiltViewModel<NewGroupViewModel>()
            NewGroupScreen(viewModel = viewModel)
        }
        composable(route = ItemScreen.NewRateScreen.name) { NewRateScreen() }
        composable<DetailsChild> { backStackEntry ->
            val ChildviewModel = hiltViewModel<NewChildViewModel>()
            val idChild = backStackEntry.toRoute<DetailsChild>()

            NewChildScreen(idChild, ChildviewModel)
        }
        composable(route = ItemScreen.NewChildScreen.name) {
            val viewModel = hiltViewModel<NewChildViewModel>()
            NewChildScreen(viewModel = viewModel)
        }
        composable(route = ItemScreen.NewVisitScreen.name) { NewVisitScreen() }
    }
}

