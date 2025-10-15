package com.catshome.classJournal


//import androidx.compose.material.Icon

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.navigate.DetailsChild
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.ScreenNoItem
import com.catshome.classJournal.screens.child.ChildListAction
import com.catshome.classJournal.screens.child.ChildListEvent
import com.catshome.classJournal.screens.child.ChildListViewModel
import com.catshome.classJournal.screens.child.ItemGroup
import com.catshome.classJournal.screens.child.itemChild

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor", "SuspiciousIndentation")
@Composable
fun ChildListScreen(
    navController: NavController,
    viewModel: ChildListViewModel = viewModel()
) {
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    LaunchedEffect(Unit){
        viewModel.obtainEvent(ChildListEvent.ReloadScreen)
    }
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
//                val v = ImageVector(painter =  painterResource(R.drawable.ic_group), contentDescription = "",
//                    modifier = Modifier.size(24.dp))
                fabMenu(listFAB = listFAB)
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = ClassJournalTheme.colors.primaryBackground)

            )
            {
                if (viewState.item.isEmpty()) {
                    ScreenNoItem(stringResource(R.string.no_child_item), bottomPadding)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = bottomPadding),
                        state = rememberLazyListState()
                    ) {
                        viewState.item.forEach{ key,group ->
                            stickyHeader{
                                ItemGroup(key) { //onClick для
                            //Проверить если кликнули на без разделе "без группы"
                                    if (!key.contains(context.getString(R.string.no_group))) {
                                        val l = viewState.item.get(key)
                                            ?.filter { it.child.childUid == "" }
                                        val i = (l?.get(0)?.child?.groupUid)
                                        i?.let {
                                            navController.navigate(DetailsGroup(it))
                                        }
                                    }
                                }
                            }

                                itemsIndexed(group) {  index, item ->
                                    if (item.child.childUid.isNotEmpty()) {
                                        itemChild(item.child.childName, item.child.childSurname, {
                                            navController.navigate(DetailsChild(item.child.childUid))
                                        })
                                    }
                                }
                        }
                    }
                }//end else NoItem
            }
        }
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
