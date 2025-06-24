package com.catshome.ClassJournal

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.ClassJournal.Screens.Group.GroupAction
import com.catshome.ClassJournal.Screens.Group.GroupEvent
import com.catshome.ClassJournal.Screens.ItemScreen
import com.catshome.ClassJournal.Screens.viewModels.GroupViewModel
import com.catshome.ClassJournal.theme.MainTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@Composable
fun GroupScreen(navController: NavController, viewModel: GroupViewModel = viewModel()) {
    // viewModel.obtainEvent()
    //TODO разобраться с event. invokate()
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val buttomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()

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
                            bottom = buttomPadding + 16.dp,
                            end = 16.dp
                        ),
                        onClick = { navController.navigate(ItemScreen.NewGroupScreen.name) }) {
                        Icon(Icons.Sharp.Add, "")
                    }
                }
            }
        )
        {

            if (viewState.listGroup.isEmpty())
                groupScreenNoItems(navController, buttomPadding = buttomPadding)
            else {

                //TODO узнать пунк по которому был тапк и получить данные group
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ClassJournalTheme.colors.primaryBackground)
                ) {
                    itemsIndexed(viewState.listGroup) { index, item ->
                        Text(
                            item.name,
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth()
                                .height(80.dp)
                                .clickable {
                                        //TODO work with Editing Group
                                    navController.navigate(ItemScreen.NewGroupScreen)
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

@Composable
fun groupScreenNoItems(navController: NavController, buttomPadding: Dp) {
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
                    .padding(bottom = buttomPadding)
                    .align(Alignment.CenterHorizontally),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.heading
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun previewGroupScreen() {
    val group = listOf("fdsaf", "fdsa", "gfhtr")
    Surface(
        Modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxWidth(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        //Column(Modifier.fillMaxSize()) {


        Scaffold(
            Modifier
                .fillMaxSize(1f)
                .background(color = ClassJournalTheme.colors.primaryText),

            bottomBar = {
                Row(Modifier.fillMaxWidth(), Arrangement.End) {
                    FloatingActionButton(
                        modifier = Modifier.padding(16.dp)
                        //    .align(AbsoluteAlignment.Right)
                        ,
                        onClick = { }) {
                        Icon(Icons.Sharp.Add, "")
                    }
                }
            }
        )
        {
            if (group.isEmpty())
//                    groupScreenNoItems(navController)
//                else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ClassJournalTheme.colors.primaryBackground)
                ) {
                    items(
                        items = group,
                        itemContent = {
                            Text(
                                it,
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .clickable {
//                                            navController.navigate(ItemScreen.NewGroupScreen.name)
                                    },
                                color = ClassJournalTheme.colors.primaryText
                            )
                        })
                }

        }
    }
}

