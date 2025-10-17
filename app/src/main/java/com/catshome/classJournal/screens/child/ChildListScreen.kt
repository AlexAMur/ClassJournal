package com.catshome.classJournal



//import androidx.compose.material.Icon

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.child.ChildListAction
import com.catshome.classJournal.screens.child.ChildListEvent
import com.catshome.classJournal.screens.child.ChildListScreenContent
import com.catshome.classJournal.screens.child.ChildListViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor", "SuspiciousIndentation")
@Composable
fun ChildListScreen(
    navController: NavController,
    viewModel: ChildListViewModel = viewModel()
) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val snackbarHostState = remember { SnackbarHostState() }
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = ClassJournalTheme.colors.primaryBackground)
    ) {
        Scaffold(
            Modifier
                .fillMaxSize()
                .background(color = ClassJournalTheme.colors.primaryBackground),
            snackbarHost = {

                SnackbarHost(snackbarHostState)
            },
            bottomBar = {
                //Создание меню для нового ребенка и новой группы
                val listFAB = listOf(
                    ItemFAB(
                        label = stringResource(R.string.new_child_menu_item),
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                        containerColor = ClassJournalTheme.colors.tintColor,
                        contentColor = ClassJournalTheme.colors.primaryText,
                        //onClick ADD CHILD
                        onClick = {
                            viewModel.obtainEvent(ChildListEvent.NewChildClicked)
                        },
                        icon = painterResource(R.drawable.icon_face_24)//Icons.Default.Face
                    ),
                    ItemFAB(
                        label = stringResource(R.string.new_group_menu_item),
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                        containerColor = ClassJournalTheme.colors.tintColor,
                        contentColor = ClassJournalTheme.colors.primaryText,
//onClick ADD GROUP
                        onClick = {
                            viewModel.obtainEvent(ChildListEvent.NewGroupClicked)
                        },
                        icon = painterResource(R.drawable.fil_group_24)
                    )
                )
                fabMenu(listFAB = listFAB)
            }
        ) {

            ChildListScreenContent(
                navController = navController,
                viewModel = viewModel,
                viewState = viewState,
                snackbarHostState

            )
        }
        when (viewAction) {
            ChildListAction.CloseScreen -> {
                viewModel.clearAction()
            }

            ChildListAction.NewChildClicked -> {
                viewModel.clearAction()
                navController.navigate(ItemScreen.NewChildScreen.name)
            }

            ChildListAction.NewGroupClicked -> {
                viewModel.clearAction()
                navController.navigate(ItemScreen.NewGroupScreen.name)
            }

            null -> {}
        }
    }
}
