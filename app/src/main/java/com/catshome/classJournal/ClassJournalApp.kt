package com.catshome.classJournal

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.catshome.classJournal.communs.FilterScreen.FilterScreen
import com.catshome.classJournal.communs.FilterScreen.FilterSetting
import com.catshome.classJournal.navigate.DetailsChild
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.navigate.DetailsPay
import com.catshome.classJournal.navigate.NewLesson
import com.catshome.classJournal.navigate.OptionFilterPaysList
import com.catshome.classJournal.navigate.SaveLesson
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.NewPayViewModel
import com.catshome.classJournal.screens.PayList.PayListScreen
import com.catshome.classJournal.screens.PayList.PayListViewModel
import com.catshome.classJournal.screens.PayList.newPayScreen
import com.catshome.classJournal.screens.Scheduler.SchedulerListEvent
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerScreen
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerViewModel
import com.catshome.classJournal.screens.Scheduler.SchedulerListScreen
import com.catshome.classJournal.screens.Scheduler.SchedulerListViewModel
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

    NavHost(navController, startDestination = ItemScreen.PayListScreen.name) {
        composable(route = ItemScreen.NewPayScreen.name) {
            //val viewModel =hiltViewModel<NewPayViewModel>()
            Log.e("CLJR", "NavHost new pay")
            val viewModel: NewPayViewModel by activity.viewModels()
            newPayScreen(viewModel)
        }

        composable<OptionFilterPaysList> { backStackEntry ->
            val optionFilterPaysList = backStackEntry.toRoute<OptionFilterPaysList>()
            val viewModel: PayListViewModel by activity.viewModels()
            PayListScreen(
                navController = navController,
                viewModel = viewModel,
                optionFilter = optionFilterPaysList
            )

        }

        composable<DetailsPay> { backStackEntry ->
            val detailsPay = backStackEntry.toRoute<DetailsPay>()
            val viewModel: PayListViewModel by activity.viewModels()
            PayListScreen(
                navController = navController,
                viewModel = viewModel,
                detailsPay = detailsPay
            )

        }

        composable(route = ItemScreen.PayListScreen.name) {

            val viewModel: PayListViewModel by activity.viewModels()      //
            PayListScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = ItemScreen.SchedulerScreen.name) {
            // val viewModel =hiltViewModel<PayListViewModel>()
            val viewModel: SchedulerListViewModel by activity.viewModels()      //
            SchedulerListScreen(navController = navController, viewModel = viewModel)

        }

        composable<SaveLesson> { backStackEntry ->
            val saveLesson = backStackEntry.toRoute<SaveLesson>()
            val viewModel: SchedulerListViewModel by activity.viewModels()      //
            SchedulerListScreen(
                navController = navController,
                viewModel = viewModel,
                saveLesson = saveLesson
            )
        }

        composable<NewLesson> { backStackEntry ->
            val newLesson = backStackEntry.toRoute<NewLesson>()
            val viewModel: NewSchedulerViewModel by activity.viewModels()
            NewSchedulerScreen(
                navController = navController,
                viewModel = viewModel,
                newLesson = newLesson
            )
        }

        composable(route = ItemScreen.NewSchedulerScreen.name) {
            val viewModel: NewSchedulerViewModel by activity.viewModels()      //
            NewSchedulerScreen(navController = navController, viewModel = viewModel)

        }

        composable<FilterSetting> { backState ->
            val setting = backState.toRoute<FilterSetting>()
            FilterScreen(navController, setting)
        }
//        composable(route = ItemScreen.FilterScreen.name) {
////            val viewModel = hiltViewModel<VisitListViewModel>()
//            FilterScreen(navController,)
//        }

        composable(route = ItemScreen.VisitListScreen.name) {
            val viewModel = hiltViewModel<VisitListViewModel>()
            visitListScreen(navController, viewModel)
        }

        composable(route = ItemScreen.MainScreen.name) {
            val viewModel = hiltViewModel<ChildListViewModel>()
            ChildListScreen(navController, viewModel = viewModel)
        }
        composable(route = ItemScreen.GroupScreen.name) {
            val viewModel = hiltViewModel<GroupViewModel>()
            GroupScreen(navController, viewModel)
        }
        composable(route = ItemScreen.RateScreen.name) { RateScreen() }
        composable(route = ItemScreen.ReportScreen.name) { ReportScreen() }

        composable<DetailsGroup> { backStackEntry ->
            val idGroup = backStackEntry.toRoute<DetailsGroup>()
            //val viewModel = hiltViewModel<NewGroupViewModel>()
            val viewModel: NewGroupViewModel by activity.viewModels()
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

