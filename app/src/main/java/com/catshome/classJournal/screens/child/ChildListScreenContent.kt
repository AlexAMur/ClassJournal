package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.collection.emptyLongSet
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.navigate.DetailsChild
import com.catshome.classJournal.navigate.DetailsGroup
import com.catshome.classJournal.screens.ScreenNoItem
import kotlinx.coroutines.launch

@Composable
fun ChildListScreenContent(
    navController: NavController,
    viewModel: ChildListViewModel = viewModel(),
    listState: LazyListState,
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
        val density = LocalDensity.current.density
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
                LaunchedEffect(listState.firstVisibleItemIndex) {
                    if (listState.firstVisibleItemIndex > 0)
                        viewModel.obtainEvent(ChildListEvent.showFAB(false))
                    else
                        viewModel.obtainEvent(ChildListEvent.showFAB(true))
                }

                    LazyColumn(
                    modifier = Modifier

                        .background(ClassJournalTheme.colors.primaryBackground)
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = bottomPadding)
                           ,
                    state = listState
                ) {


                    viewState.item.forEach { key, group ->
                        stickyHeader {
                            if (!key.contains(context.getString(R.string.no_group))) {// Без группы
                                Row(
                                    modifier = Modifier.padding(
                                        bottom = 8.dp,
                                        top = 8.dp)

                                ) {

                                    SwipeableItemWithActions(
                                        isRevealed = group.filter {
                                            it.child.groupName == key && it.child.childUid == ""
                                        }[0].isOptionsRevealed,
                                        onExpanded = {
                                            viewModel.obtainEvent(
                                                ChildListEvent.ChangeRevealed(
                                                    item = group.filter {
                                                        it.child.groupName == key && it.child.childUid == ""
                                                    }[0],
                                                    key = key,
                                                    isOptionsRevealed = true
                                                )
                                            )
                                        },
                                        onCollapsed = {
                                            viewModel.obtainEvent(
                                                ChildListEvent.ChangeRevealed(
                                                    item = group.filter {
                                                        it.child.groupName == key && it.child.childUid == ""
                                                    }[0],
                                                    key = key,
                                                    isOptionsRevealed = false
                                                )
                                            )

                                        },
                                        actions = {
                                            //действие для удаления группы
                                            ActionIcon(
                                                onClick = {
                                                    scope.launch {
                                                        viewModel.obtainEvent(
                                                            ChildListEvent.deleteClicked(
                                                                key = key,
                                                                uidGroup = group.filter { it.child.groupName == key && it.child.childUid == "" }[0].child.groupUid,
                                                                uidChild = ""
                                                            )
                                                        )
                                                        viewModel.obtainEvent(
                                                            ChildListEvent.showSnackBar(
                                                                message = "${context.getString(R.string.message_cancel)} ${group.first().child.groupName} ?",
                                                                actionLabel = context.getString(R.string.bottom_cancel),
                                                                onDismissed = {
                                                                    viewModel.obtainEvent(
                                                                        ChildListEvent.deleteGroup(
                                                                            viewState.uidDelete
                                                                        )
                                                                    )
                                                                },
                                                                onActionPerformed = {
                                                                    //Сброс удаления
                                                                    viewModel.obtainEvent(
                                                                        ChildListEvent.undoDelete
                                                                    )
                                                                }
                                                            ))

                                                    }
                                                },
                                                icon = Icons.Default.Delete,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(120.dp)
                                            )
                                        },
                                    ) { offset->
                                        //отрисовка контента группы,
                                        // без группы рисуется ниже в блоке else
                                        ItemGroup(offset= offset,   nameGroup = key) { //onClick для открытия на редактированя группы
                                            //Проверить если кликнули на без разделе "без группы"
                                            if (!key.contains(context.getString(R.string.no_group))) {
                                                val l = viewState.item.get(key)
                                                    ?.filter { it.child.childUid == "" }
                                                val i = (l?.get(0)?.child?.groupUid)
                                                i?.let {
                                                    viewModel.obtainEvent(ChildListEvent.SelectItem)
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
                                    ItemGroup(offset= 0f,
                                        nameGroup = stringResource(R.string.no_group),
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
                                            viewModel.obtainEvent(
                                                ChildListEvent.ChangeRevealed(
                                                    item = item,
                                                    key = key,
                                                    isOptionsRevealed = true
                                                )
                                            )
                                        },
                                        onCollapsed = {
                                            //сохранить закрытие в статусе
                                            viewModel.obtainEvent(
                                                ChildListEvent.ChangeRevealed(
                                                    item = item,
                                                    key = key,
                                                    isOptionsRevealed = false
                                                )
                                            )
                                        },
                                        actions = {
                                            ActionIcon(
                                                onClick = {
                                                    //Удаление ребенка из списка
                                                    viewModel.obtainEvent(
                                                        ChildListEvent.deleteClicked(
                                                            uidChild = item.child.childUid,
                                                            uidGroup = "",
                                                            key = key
                                                        )
                                                    )
//                                                    scope.launch {
                                                    viewModel.obtainEvent(
                                                        ChildListEvent.showSnackBar(
                                                            message = "${context.getString(R.string.message_cancel)} ${item.child.childName} ?",
                                                            actionLabel = context.getString(R.string.bottom_cancel),
                                                            onDismissed = {
                                                                viewModel.obtainEvent(
                                                                    ChildListEvent.deleteChild(
                                                                        uid = item.child.childUid,
                                                                        key = key
                                                                    )
                                                                )
                                                            },
                                                            onActionPerformed = {
                                                                viewModel.obtainEvent(ChildListEvent.undoDelete)
                                                            }

                                                        ))
                                                },
                                                icon = Icons.Default.Delete,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(120.dp)
                                                    .background(ClassJournalTheme.colors.primaryBackground)
                                            )
                                        },
                                    ) { offset->
                                        //Отрисовка контента  ребенка
                                        itemChild(
                                            offset = offset,
                                            item.child.childName,
                                            item.child.childSurname
                                        )
                                        {
                                            viewModel.obtainEvent(ChildListEvent.SelectItem)
                                            navController.navigate(DetailsChild(item.child.childUid))
                                        }
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