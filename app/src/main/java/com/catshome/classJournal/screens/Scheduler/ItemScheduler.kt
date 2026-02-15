package com.catshome.classJournal.screens.Scheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.SwipeToDeleteContainer
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.toTimeString

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun itemScheduler(
    day: DayOfWeek,
    itemsMap: Map<Long, List<Scheduler>?>?,
    viewModel: SchedulerListViewModel,
    collapsItem: (Int) -> Unit,
    newTime: (Int) -> Unit,
    editTime: (Int) -> Unit,
    addMember: () -> Unit
) {
    val viewState by viewModel.viewState().collectAsState()
    val context = LocalContext.current
    val index = DayOfWeek.entries.indexOf(day)
//    Card(
//        Modifier
//            .fillMaxWidth()
//            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
//        //    .clickable(onClick = onClick)
//        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
//        shape = ClassJournalTheme.shapes.cornersStyle,
//        // border = BorderStroke(width = 1.dp, color = ClassJournalTheme.colors.tintColor)
//    )
//    {
    Column(
        Modifier.fillMaxWidth()
    ) {
//            val insets = WindowInsets.safeDrawing.asPaddingValues()
//            Text("Доступная высота (примерно): ${insets.calculateBottomPadding() + insets.calculateTopPadding()}")
        Row(
            Modifier
                .background(ClassJournalTheme.colors.controlColor)
                .clickable(
                    onClick = { collapsItem(index) }
//                        {
//                        viewModel.obtainEvent(SchedulerListEvent.CollapseItem(0))
//                        Log.e("CLJR",viewState.daylist.toString())
//                    }
                )) {
            Text(
                day.shortName,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.8f),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.toolbar
            )
            Icon(
                imageVector = if (viewState.dayList[DayOfWeek.entries.indexOf(day)]) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
        if (viewState.dayList[index]) {
            itemsMap?.forEach { (timeLessonKey, lists) ->
                LazyColumn(
                    Modifier
                        .heightIn(min = 56.dp, max = 500.dp)
                        .background(ClassJournalTheme.colors.disableColor)
                ) {
                    stickyHeader {
                        SwipeToDeleteContainer(
                            item = timeLessonKey.toTimeString(),
                            onDelete = { deleteItem ->
                                // if (deleteItem == value.scheduler.name)
                                viewModel.obtainEvent(
                                    SchedulerListEvent.DeleteSwipe(
                                        type = ItemType.lesson,
                                        key = day.shortName,
                                        scheduler = Scheduler(
                                            dayOfWeek = day.shortName,
                                            dayOfWeekInt = day.ordinal,
                                            startLesson = timeLessonKey
                                        ),
                                        dayOfWeek = day,
                                        context = context
                                    )
                                )
                            }
                        ) { name ->//тут контент
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .background(color = ClassJournalTheme.colors.disableColor),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = timeLessonKey.toTimeString(),
                                    modifier = Modifier
                                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                                        .clickable(onClick = { editTime(index) }),
                                    color = ClassJournalTheme.colors.primaryText,
                                    style = ClassJournalTheme.typography.caption,
                                )
                                TextButton(
                                    modifier = Modifier,
                                    onClick = addMember
                                ) {
                                    Icon(
                                        Icons.TwoTone.Add,
                                        contentDescription = stringResource(R.string.add_scheduler_),
                                        tint = ClassJournalTheme.colors.tintColor
                                    )
                                }
                            }
                        }
                    }
                    lists?.let { listsScheduler ->
                        items(
                            listsScheduler,
                            key = { it.uid!! }
                        ) { value ->
                            value.name?.let { text ->
                                SwipeToDeleteContainer(
                                    item = text,
                                    onDelete = {
                                        viewModel.obtainEvent(
                                            SchedulerListEvent.DeleteSwipe(
                                                type = ItemType.client,
                                                key = day.shortName,
                                                scheduler = value,
                                                context = context,
                                                dayOfWeek = day
                                            )
                                        )
                                    }
                                ) { name ->
                                    ItemListScheduler(name) //можно добввить onClick
                                }
                            }
                        }
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(
                            bottom = 16.dp,
                            end = 16.dp
                        ),
                    onClick = { newTime(index) } //запуск  диалога выбора времени
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_add_card_24),
                        stringResource(R.string.no_scheduler)
                    )
                }
            }
        }
    }
}