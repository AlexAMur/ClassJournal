package com.catshome.classJournal.screens.Scheduler

import android.app.LocaleConfig
import android.util.Log
import android.view.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.toTimeString
import java.util.Locale
import kotlin.collections.forEachIndexed

@Composable
fun itemScheduler(
    day: DayOfWeek,
    items: Map<Long, List<Scheduler>?>?,
    viewModel: SchedulerListViewModel,
    collapsItem: (Int) -> Unit,
    newTime: (Int) -> Unit,
    editTime: (Int) -> Unit,
    newMember: () -> Unit
) {
    val viewState by viewModel.viewState().collectAsState()
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
            items?.forEach { (key, lists) ->
                LazyColumn(
                    Modifier
                        .heightIn(min = 56.dp, max = 500.dp)
                        .background(ClassJournalTheme.colors.disableColor)
                ) {
                    stickyHeader {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(color = ClassJournalTheme.colors.disableColor),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = key.toTimeString(),
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                                    .clickable(onClick = {editTime(index)}),
                                color = ClassJournalTheme.colors.primaryText,
                                style = ClassJournalTheme.typography.caption,
                                )
                            TextButton(
                                modifier = Modifier,
                                onClick = newMember
                            ) {
                                Icon(
                                    Icons.TwoTone.Add,
                                    contentDescription = stringResource(R.string.add_scheduler_),
                                    tint = ClassJournalTheme.colors.tintColor
                                )
                            }
                        }
                    }
                    lists?.let { list ->
                        itemsIndexed(list) { index, item ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp)
                                    .background(ClassJournalTheme.colors.secondaryBackground),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    modifier = Modifier
                                        .weight(0.8f)
                                        .padding(start = 8.dp),
                                    text = item.name?:"",
                                    style = ClassJournalTheme.typography.body,

                                    )
                                IconButton(onClick = {//TODO delete item childItems scheduler
                                     }) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = null,
                                        tint = ClassJournalTheme.colors.tintColor
                                    )
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