package com.catshome.classJournal.screens.Visit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.navigate.VisitDetails


@Composable
fun visitListScreen(navController: NavController, viewModel: VisitListViewModel = viewModel()){
     val viewAction by viewModel.viewActions().collectAsState(null)

    when(viewAction){
        VisitListAction.NewVisit -> {
            navController.navigate(VisitDetails())
        }
        null -> TODO()
    }
}