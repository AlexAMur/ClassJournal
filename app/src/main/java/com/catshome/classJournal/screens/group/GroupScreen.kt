package com.catshome.classJournal.screens.group


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.rememberSwipeToDismissBoxState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealState
import androidx.wear.compose.material.RevealValue
import androidx.wear.compose.material.rememberRevealState
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.screens.viewModels.GroupViewModel


@OptIn(ExperimentalWearMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@Composable
fun GroupScreen(navController: NavController, viewModel: GroupViewModel = viewModel()) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
   // var revealState = rememberRevealState()
    //if (viewState.listItems[index].revealState != null)
    //   revealState= viewState.listItems[index].revealState

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState { it ->
        Log.e("CLJR", "DISSMIS STATE")
        true
    }
    LaunchedEffect(Unit) {
        viewModel.obtainEvent(GroupEvent.ReloadScreen)
    }
    Surface(
        Modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxWidth(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        Scaffold(
            Modifier
                .fillMaxWidth()
                .background(color = ClassJournalTheme.colors.primaryBackground),

            bottomBar = {
                Row(Modifier.fillMaxWidth(), Arrangement.End) {
                    FloatingActionButton(
                        modifier = Modifier.padding(
                            bottom = bottomPadding + 16.dp,
                            end = 16.dp
                        ),
                        onClick = { viewModel.obtainEvent(GroupEvent.NewClicked) }) {
                        Icon(Icons.Sharp.Add, "")
                    }
                }
            }
        ) {
            if (viewState.listItems.isEmpty())
                groupScreenNoItems(bottomPadding = bottomPadding)
            else {
                Column(
                    Modifier
                        .background(ClassJournalTheme.colors.primaryBackground)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp)
                            .background(ClassJournalTheme.colors.primaryBackground)
                    ) {
                        itemsIndexed(viewState.listItems) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                colors = CardDefaults.cardColors(ClassJournalTheme.colors.tintColor),
                                shape = ClassJournalTheme.shapes.cornersStyle

                            ) {

                                ChipGroup(
                                    viewModel = viewModel,
                                    index = index,
                                    //revealState = revealState,
                                    //swipeToDismissBoxState = swipeToDismissBoxState
                                    //if(viewState.listItems[index].uid == viewState.swipeUid) (viewState.revealState) as RevealState
                                                    //        else rememberRevealState()
                                ) {

                                    Box {
                                        Log.e("CLJR", "Text event SwipeUpdate")
                                        Text(
                                            item.group.name,
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                                .clickable {
                                                    navController.navigate(DetailsGroup(viewState.listItems[index].group.uid))
                                                },
                                            color = ClassJournalTheme.colors.primaryText,
                                            style = ClassJournalTheme.typography.heading
                                        )
                                    }

                                }
                            }
                        }
                    }

                }
            }
            when (viewAction) {
                is GroupAction.DeleteGroup -> {
                }

                is GroupAction.OpenGroup -> {
                    viewModel.clearAction()
                    navController.navigate(DetailsGroup(0))
                }

                is GroupAction.RequestDelete -> {}
                null -> {}
            }
        }
    }
}

@Composable
fun groupScreenNoItems(bottomPadding: Dp) {
    Surface(
        Modifier
            .fillMaxSize()
            .background(ClassJournalTheme.colors.primaryBackground),
        color = ClassJournalTheme.colors.primaryBackground

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(ClassJournalTheme.colors.primaryBackground)
        ) {
            Text(
                stringResource(R.string.no_group_item),
                modifier = Modifier
                    .padding(bottom = bottomPadding)
                    .align(Alignment.CenterHorizontally),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.heading
            )
        }
    }
}