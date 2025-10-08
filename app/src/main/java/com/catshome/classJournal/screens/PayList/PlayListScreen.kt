package com.catshome.classJournal.screens.PayList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.screens.group.GroupAction

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlayListScreen(
    navController: NavController,
    viewModel: PayListViewModel = viewModel()
) {
    var mupdate = true
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()

//    LaunchedEffect(Unit) {
//        //TODO Called twice
//        viewModel.obtainEvent(PayListEvent.ReloadScreen)
//    }
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
                        onClick = { viewModel.obtainEvent(PayListEvent.NewClicked) }) {
                        Icon(Icons.Sharp.Add, "")
                    }
                }
            }
        ) { padValues ->
            if (viewState.items.isEmpty())
                payScreenNoItems(bottomPadding =   padValues.calculateBottomPadding()
                )// bottomPadding)
            else {
                Column(
                    Modifier
                        .background(ClassJournalTheme.colors.primaryBackground)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp, bottom = bottomPadding)
                            .background(ClassJournalTheme.colors.primaryBackground),
                        state = rememberLazyListState()
                    ) {
                        itemsIndexed(viewState.items) { index, item ->
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth(),
//
//                            //        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
//                                colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
//                                shape = ClassJournalTheme.shapes.cornersStyle
//                            ) {
//                                SwipeToDismissListItems(
//                                    onEndToStart = {
//                                        viewModel.obtainEvent(
//                                            PayListEvent.DeleteClicked(
//                                                viewState.items[index].uidPay,
//                                                index
//                                            )
//                                        )
//                                    }
//                                ) {

                                    itemPay(
                                        fio = viewState.items[index].nameSurname,
                                         date = viewState.items[index].datePay,
                                       payment = viewState.items[index].payment
                                    ){

                                    }
 //                                    Box {
//                                        Text(
//                                            viewState.items[index].nameSurname,
//                                            modifier = Modifier
//                                                .padding(16.dp)
//                                                .fillMaxWidth()
//                                                .clickable {
//                                                    navController.navigate(
//                                                        DetailsGroup(
//                                                            viewState.items[index].uidPay
//                                                        )
//                                                    )
//                                                },
//                                            color = ClassJournalTheme.colors.primaryText,
//                                            style = ClassJournalTheme.typography.heading
//                                        )
//                                    }
//                                }
//                            }
                        }
                    }
                }
            }
            when (viewAction) {
                is GroupAction.DeleteGroup -> {}
                is GroupAction.OpenGroup -> {
                    viewModel.clearAction()
                    navController.navigate(DetailsGroup(""))
                }

                is GroupAction.RequestDelete -> {
                    mupdate = !mupdate

                    // viewState.listItems[(viewAction as GroupAction.RequestDelete).index].revealState = rememberRevealState()
                }

                null -> {}
            }
        }
    }
}


@Composable
fun itemPay(fio: String, date: String, payment:  Int,onClick:()->Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 8.dp, end = 16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
        shape = ClassJournalTheme.shapes.cornersStyle
    )

    {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column(Modifier.fillMaxWidth(0.75f)) {
                Text(
                    fio,
                    modifier = Modifier
                       .padding(start =16.dp, top = 8.dp, bottom = 8.dp)
                       .fillMaxWidth(),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.body
                )

                Text(
                    stringResource(R.string.paymant_date)+" $date",
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.caption
                )

            }
            Text(
                "${payment} p.",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.body
            )
        }
    }
}

@Composable
fun payScreenNoItems(bottomPadding: Dp) {
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
                stringResource(R.string.no_pay_item),
                modifier = Modifier
                    .padding(bottom = bottomPadding)
                    .align(Alignment.CenterHorizontally),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.heading
            )
        }
    }
}