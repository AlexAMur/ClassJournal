package com.catshome.classJournal.screens.group


import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.wear.compose.foundation.edgeSwipeToDismiss
import androidx.wear.compose.foundation.rememberSwipeToDismissBoxState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealValue
import androidx.wear.compose.material.SwipeToRevealChip
import androidx.wear.compose.material.SwipeToRevealDefaults
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import androidx.wear.compose.material.SwipeToRevealUndoAction
import androidx.wear.compose.material.rememberRevealState
import com.catshome.ClassJournal.R
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.screens.viewModels.GroupViewModel
import com.catshome.classJournal.navigate.DetailsGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalWearMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@Composable
fun GroupScreen(navController: NavController, viewModel: GroupViewModel = viewModel()) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
    LaunchedEffect(Unit) {
        viewModel.obtainEvent(GroupEvent.ReloadScreen)
    }
    Surface(
        Modifier.background(ClassJournalTheme.colors.primaryBackground)
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
        )
        {

            if (viewState.listGroup.isEmpty())
                groupScreenNoItems(bottomPadding = bottomPadding)
            else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                        .background(ClassJournalTheme.colors.primaryBackground)
                ) {
                    itemsIndexed(viewState.listGroup) { index, item ->

                        Chip(
                            viewModel = viewModel,
                            index = index,
                            name = item.name,
                            navController = navController
                        )
                    }
                }

            }
        }
        when (viewAction) {
            ComposeAction.Success -> {}
            ComposeAction.New -> {
                viewModel.clearAction()
                navController.navigate(DetailsGroup(0))
            }

            ComposeAction.CloseScreen -> {
//            keyboardController?.hide()
//            outerNavigation.popBackStack()
//            androidx.lifecycle.viewmodel.compose.viewModel.clearAction()
            }

            is GroupAction.DeleteGroup -> {

            }

            is GroupAction.OpenGroup -> {
                viewModel.clearAction()
                navController.navigate(DetailsGroup(0))
            }

            is GroupAction.RequestDelete -> {
//                val openDialog = remember{mutableStateOf(true) }
//                if (openDialog.value) {
//                    DeleteDialog(
//                        onConfirmation = {
//                            viewModel.obtainEvent(GroupEvent.DeleteClicked(viewState.uidDelete))
//
//                        }
//                    ) {
//                        viewModel.obtainEvent(GroupEvent.CancelDelete)
//                        openDialog.value =false
//                    }
//                }
            }

            null -> {}
        }
    }
}

//
//@Composable
//fun itemWithOutButton(name: String, onClicks: () -> Unit) {
//    Text(
//        name,
//        modifier = Modifier
//            .padding(24.dp)
//            .fillMaxWidth()
//            .height(56.dp)
//            .clickable(onClick = onClicks),
//        color = ClassJournalTheme.colors.primaryText,
//        style = ClassJournalTheme.typography.heading
//
//    )
//}

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

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun Chip(viewModel: GroupViewModel, index: Int, name: String, navController: NavController) {
    val viewState by viewModel.viewState().collectAsState()
    val revealState = rememberRevealState()
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    SwipeToRevealChip(
        revealState = revealState,
        modifier = Modifier.edgeSwipeToDismiss(swipeToDismissBoxState = swipeToDismissBoxState),
        primaryAction = {
            SwipeToRevealPrimaryAction(
                revealState = revealState,
                icon = {
                    Icon(
                        SwipeToRevealDefaults.Delete,
                        contentDescription = "Delete"
                    )
                },
                label = {
                    Text(
                        "Delete",
                        color = ClassJournalTheme.colors.primaryText
                    )
                },
                onClick = {

//                    viewModel.obtainEvent(
//                        GroupEvent.DeleteClicked(
//                            viewState.listGroup[index].uid
//                        )
//                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        revealState.snapTo(RevealValue.RightRevealed)
                            delay(4000L)
                            viewModel.obtainEvent(GroupEvent.DeleteClicked(viewState.listGroup[index].uid))
                         revealState.snapTo(RevealValue.Covered)
                    }
                    //viewModel.obtainEvent(GroupEvent.ReloadScreen)
                })

        },
        undoPrimaryAction = {
            SwipeToRevealUndoAction(
                modifier = Modifier.background(ClassJournalTheme.colors.controlColor),
                revealState = revealState,
                label = { Text("Undo") },
                onClick = {
                    viewModel.obtainEvent(
                        GroupEvent.UndoDeleteClicked(
                            viewState.listGroup[index].uid
                        )
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        revealState.snapTo(RevealValue.Covered)
                    }
                    viewModel.obtainEvent(GroupEvent.ReloadScreen)
                }
            )
        },
        onFullSwipe = {}
    ) {
        Box {
            Text(
                name,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        navController.navigate(DetailsGroup(viewState.listGroup[index].uid))
                    },
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.heading
            )
        }
    }
}

//@Composable
//fun itemWithButton(name: String, Click: () -> Unit, onDeleteClick: () -> Unit) {
//    Row(
//        Modifier
//            .fillMaxWidth()
//            .background(ClassJournalTheme.colors.primaryBackground),
//        horizontalArrangement = Arrangement.End
//    ) {
//        Text(
//            name,
//            modifier = Modifier
//                .padding(start = 24.dp)
//                .weight(1f, fill = true)
//                .height(80.dp)
//                .clickable(onClick = Click),
//            color = ClassJournalTheme.colors.primaryText,
//            style = ClassJournalTheme.typography.heading
//
//        )
//        Button(onClick = onDeleteClick, Modifier.padding(start = 16.dp, end = 16.dp)) {
//            Icon(Icons.Outlined.Delete, "")
//        }
//    }
//
//}


//
//val swipeableState = rememberSwipeableState(initialValue = 0)
//val size = with(LocalDensity.current) { 100.dp.toPx() }
//val anchors = mapOf(
//    0f to 0,
//    -size to 1
//)   // 0 - исходное положение, -size - раскрытое положение
//
//val context = LocalContext.current
//
//Box(
//modifier = Modifier
//.fillMaxWidth()
//.height(80.dp)
//.swipeable(
//state = swipeableState,
//anchors = anchors,
//thresholds = { _, _ -> FractionalThreshold(0.9f) },
//orientation = Orientation.Horizontal
//)
//) {
//    // Показать область позади элемента
//    Box(
//        modifier = Modifier
//            .fillMaxHeight()
//            .width(200.dp)
//            .align(Alignment.CenterEnd)
//            .background(ClassJournalTheme.colors.secondaryBackground)
//    ) {
//        Row(Modifier.fillMaxSize().background(ClassJournalTheme.colors.tintColor)) {
//            Icon(
//                imageVector = Icons.Default.Edit,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(56.dp)
//                    .clickable(onClick = {
//                    }),
//                tint = ClassJournalTheme.colors.controlColor
//            )
//            Spacer(modifier = Modifier.width(24.dp))
//            Icon(
//                imageVector = Icons.Outlined.Delete,
//                contentDescription = null,
//                modifier = Modifier
//                    .size
//                        (56.dp)
//                    .clickable(onClick = {
//                        viewModel.obtainEvent(
//                            GroupEvent.DeleteClicked(viewState.listGroup[index].uid)
//                        )
//                        Toast
//                            .makeText(
//                                context,
//                                "User removed",
//                                Toast.LENGTH_SHORT
//                            )
//                            .show()
//                        CoroutineScope(Dispatchers.Main).launch {
//                            swipeableState.snapTo(0)
//                        }
//
//                    }),
//                tint = ClassJournalTheme.colors.errorColor
//            )
//
//        }
//    }
//
//    // Основной контент, который перемещается при смахивании
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .offset(x = swipeableState.offset.value.dp)
//            .background(ClassJournalTheme.colors.primaryBackground)
//    ) {
//        Text(
//            item.name,
//            modifier = Modifier
//                .padding(24.dp)
//                .fillMaxWidth()
//                .height(56.dp)
//                .clickable {
//                    navController.navigate(DetailsGroup(viewState.listGroup[index].uid))
//                },
//            color = ClassJournalTheme.colors.primaryText,
//            style = ClassJournalTheme.typography.heading
//        )
//    }


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

