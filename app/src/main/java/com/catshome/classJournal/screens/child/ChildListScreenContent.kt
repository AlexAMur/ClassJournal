package com.catshome.classJournal.screens.child

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.ActionIcon
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.SwipeableItemWithActions
import com.catshome.classJournal.context
import com.catshome.classJournal.navigate.DetailsChild
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.screens.ScreenNoItem
import kotlinx.coroutines.launch
import kotlin.collections.filter

@Composable
fun ChildListScreenContent(
    navController: NavController,
    viewModel: ChildListViewModel = viewModel(),
    viewState: ChildListState,
    snackbarState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()

    Surface(
        modifier = Modifier.background(
            ClassJournalTheme.colors.primaryBackground
        )
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
                        .background(ClassJournalTheme.colors.primaryBackground)
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = bottomPadding),
                    state = rememberLazyListState()
                ) {
                    viewState.item.forEach { key, group ->
                        stickyHeader {
                            if (!key.contains(context.getString(R.string.no_group))) {// Без группы
                                Row(
                                    modifier = Modifier.padding(
                                        bottom = 8.dp,
                                        top = 8.dp
                                    )
                                ) {
                                    SwipeableItemWithActions(
                                        isRevealed = group.filter {
                                            it.child.groupName == key && it.child.childUid == ""
                                        }[0].isOptionsRevealed,
                                        onExpanded = {
                                            viewState.item.put(
                                                key = key, ChangeOptionsRevealed(
                                                    item = viewState.item,
                                                    childItem = group.filter {
                                                        it.child.groupName == key && it.child.childUid == ""
                                                    }[0],
                                                    key = key,
                                                    isOptionsRevealed = true
                                                )
                                            )
                                        },
                                        onCollapsed = {
                                            viewState.item.put(
                                                key = key, ChangeOptionsRevealed(
                                                    item = viewState.item,
                                                    childItem = group.filter {
                                                        it.child.groupName == key && it.child.childUid == ""
                                                    }[0],
                                                    key = key,
                                                    isOptionsRevealed = false
                                                )
                                            )
                                        },
                                        actions = {
                                            ActionIcon(
                                                onClick = {
                                                    scope.launch {
                                                        when (SnackBarAction(
                                                            message = group.first().child.groupName,
                                                            actionLabel = context.getString(R.string.bottom_cancel),
                                                            snackbarState = snackbarState
                                                        )) {
                                                            SnackbarResult.Dismissed -> TODO()
                                                            SnackbarResult.ActionPerformed -> TODO()
                                                        }
                                                    }

//                                                    viewModel.obtainEvent(
//                                                        ChildListEvent.DeleteGroupClicked(
//                                                            group.first().child.groupUid
//                                                        )
//                                                    )
                                                },
                                                //backgroundColor = ClassJournalTheme.colors.primaryBackground,
                                                icon = Icons.Default.Delete,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                            )
                                        },
                                        // modifier = Modifier.background(ClassJournalTheme.colors.primaryBackground)
                                    ) {
                                        //отрисовка контента группы,
                                        // без группы рисуется ниже в блоке else
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
                                }
                            } else {
                                //Рисуем без группы  на него не придусмотренно нажатие
                                Row(
                                    modifier = Modifier.padding(
                                        bottom = 8.dp,
                                        top = 8.dp
                                    )
                                ) {
                                    ItemGroup(
                                        stringResource(R.string.no_group),
                                        onClick = {})
                                }

                            }
                        }
                        itemsIndexed(group) { index, item ->
                            //если childUid пустой значит это граппа и выовдить не нужно
                            if (item.child.childUid.isNotEmpty()) {
                                Row(
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        bottom = 8.dp,
                                        end = 16.dp
                                    )
                                ) {
                                    SwipeableItemWithActions(
                                        isRevealed = item.isOptionsRevealed,
                                        onExpanded = {
                                            //сохранить открытие в статусе
                                            viewState.item.put(
                                                key = key, ChangeOptionsRevealed(
                                                    item = viewState.item,
                                                    childItem = item,
                                                    key = key,
                                                    isOptionsRevealed = true
                                                )
                                            )

                                        },
                                        onCollapsed = {
                                            //сохранить закрытие в статусе
                                            viewState.item.put(
                                                key = key, ChangeOptionsRevealed(
                                                    item = viewState.item,
                                                    childItem = item,
                                                    key = key,
                                                    isOptionsRevealed = false
                                                )
                                            )

                                        },
                                        actions = {
                                            ActionIcon(
                                                onClick = {
                                //Удаление ребенка из списка
                                                    scope.launch {
                                                        when (SnackBarAction(
                                                            message = item.child.childName,
                                                            actionLabel = context.getString(R.string.bottom_cancel),
                                                            snackbarState = snackbarState
                                                        )) {
                                                            SnackbarResult.Dismissed -> TODO()
                                                            SnackbarResult.ActionPerformed -> TODO()
                                                        }
                                                    }
                                                },
                                                //   backgroundColor = ClassJournalTheme.colors.primaryBackground,
                                                icon = Icons.Default.Delete,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    //.padding()
                                                    .background(ClassJournalTheme.colors.primaryBackground)
                                            )
                                        },
                                    ) {
                                        //Отрисовка контента  ребенка
                                        itemChild(
                                            item.child.childName,
                                            item.child.childSurname,
                                            {
                                                navController.navigate(DetailsChild(item.child.childUid))
                                            })
                                    }
                                }
                            }
                        }
                    }
                }
            }//end else NoItem
        }
    }
}