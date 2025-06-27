package com.catshome.ClassJournal

import android.R.attr.onClick
import android.annotation.SuppressLint
import androidx.collection.intFloatMapOf
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.ClassJournal.Screens.Group.ComposeAction
import com.catshome.ClassJournal.Screens.Group.GroupAction
import com.catshome.ClassJournal.Screens.Group.GroupEvent
import com.catshome.ClassJournal.Screens.ItemScreen
import com.catshome.ClassJournal.Screens.viewModels.GroupViewModel
import com.catshome.ClassJournal.domain.Group.Models.Group
import com.catshome.ClassJournal.navigate.DetailsGroup
import java.nio.file.WatchEvent
import kotlin.text.get


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@Composable
fun GroupScreen(navController: NavController, viewModel: GroupViewModel = viewModel()) {
    // viewModel.obtainEvent()
    //TODO разобраться с event. invokate()
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val buttomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
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
                            bottom = buttomPadding + 16.dp,
                            end = 16.dp
                        ),
                        onClick = { viewModel.obtainEvent(GroupEvent.NewClicked) }) {
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
                        .padding(top = 24.dp)

                        .background(ClassJournalTheme.colors.primaryBackground)
                ) {
                    itemsIndexed(viewState.listGroup) { index, item ->

                       itemWithOutButton(
                                        item.name,
                                        {
                                            navController.navigate(DetailsGroup(viewState.listGroup[index].uid))
                                        })
                    }
                }
            }
        }
    }

    when (viewAction) {
        ComposeAction.Success -> { }
        ComposeAction.New->{
            viewModel.clearAction()
            navController.navigate(DetailsGroup(0))

        }
        ComposeAction.CloseScreen -> {
//            keyboardController?.hide()
//            outerNavigation.popBackStack()
//            androidx.lifecycle.viewmodel.compose.viewModel.clearAction()
        }

        null -> {}
        is GroupAction.DeleteGroup -> TODO()
        is GroupAction.OpenGroup -> {
            viewModel.clearAction()
            navController.navigate(DetailsGroup(0))
        }
    }
}
@Composable
fun ItemList(){

}

@Composable
fun itemWithOutButton(name: String, onClicks: () -> Unit) {
    Text(
        name,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClicks),
        color = ClassJournalTheme.colors.primaryText,
        style = ClassJournalTheme.typography.heading

    )
}

@Composable
fun itemWithButton(name: String, Click: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(ClassJournalTheme.colors.primaryBackground),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            name,
            modifier = Modifier
                .padding(start = 24.dp)
                .weight(1f, fill = true)
                .height(80.dp)
                .clickable(onClick = Click),
            color = ClassJournalTheme.colors.primaryText,
            style = ClassJournalTheme.typography.heading

        )
        Button(onClick = onDeleteClick, Modifier.padding(start = 16.dp, end = 16.dp)) {
            Icon(Icons.Outlined.Delete, "")
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


//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview
//@Composable
//fun previewGroupScreen() {
//    val group = listOf("fdsaf", "fdsa", "gfhtr")
//    Surface(
//        Modifier
//            .background(ClassJournalTheme.colors.primaryBackground)
//            .fillMaxWidth(),
//        color = ClassJournalTheme.colors.primaryBackground
//    ) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(ClassJournalTheme.colors.primaryBackground)
//        ) {
//            itemsIndexed(group) { index, item ->
//
//                Text(item, modifier = Modifier.fillMaxWidth())
//                //itemWithOutButton(name, {})
//            }
//        }
//
//    }
//
//}
//
//
