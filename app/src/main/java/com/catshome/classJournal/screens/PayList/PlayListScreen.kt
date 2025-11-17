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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.child.ChildListEvent
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
    DisposableEffect(Unit) {
        onDispose {
            viewModel.obtainEvent(PayListEvent.ShowFAB(false))
        }
    }
    Surface(
        Modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxWidth(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        viewModel.obtainEvent(PayListEvent.ShowFAB(true))
        Scaffold(
            Modifier
                .fillMaxWidth()
                .background(color = ClassJournalTheme.colors.primaryBackground),

            bottomBar = {
                Row(Modifier.fillMaxWidth(), Arrangement.End) {
                    fabMenu(
                        listFAB = listOf(ItemFAB(
                            containerColor =  ClassJournalTheme.colors.tintColor,
                            contentColor = ClassJournalTheme.colors.secondaryBackground,
                            icon = painterResource(R.drawable.rusrub_48),//R.drawable.pay),
                            onClick = {
                                viewModel.obtainEvent(PayListEvent.NewClicked)
                            }
                        )),
                        fabVisible = viewState.showFAB
                    )

//                    FloatingActionButton(
//                        modifier = Modifier.padding(
//                            bottom = bottomPadding + 16.dp,
//                            end = 16.dp
//                        ),
//                        onClick = { viewModel.obtainEvent(PayListEvent.NewClicked) }) {
//                        Icon( painter = painterResource(R.drawable.rusrub_24)
//                            , "")
//                    }
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

                                    itemPay(
                                        fio = viewState.items[index].surname,
                                         date = viewState.items[index].datePay,
                                       payment = viewState.items[index].payment
                                    ){

                                    }
                        }
                    }
                }
            }
            when (viewAction) {
                is PayListAction.NewPay -> {
                    navController.navigate(ItemScreen.NewPayScreen.name)
                    viewModel.clearAction()
                }
                null -> {}
            }
        }
    }
}


@Composable
fun itemPay(fio: String, date: String, payment:  String,onClick:()->Unit) {
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