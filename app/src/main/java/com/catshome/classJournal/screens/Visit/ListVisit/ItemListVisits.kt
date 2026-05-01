package com.catshome.classJournal.screens.Visit.ListVisit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.communs.SwipeToDeleteContainer
import com.catshome.classJournal.domain.Visit.Visit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemListVisits(
    visit: Visit,
    onDeleteItem: (Visit) -> Unit,
    onClick: () -> Unit
) {
    visit.isDelete?.let {
        if (!it) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.End
            ) {
                SwipeToDeleteContainer(
                    item = visit,
                    onDelete = { onDeleteItem(visit) }
                ) { Visit ->

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(ClassJournalTheme.colors.primaryBackground)
                            .padding(top = 16.dp, bottom = 12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            Modifier
                                .clickable(onClick= onClick)
                                . fillMaxWidth (),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = visit.fio,
                                color = ClassJournalTheme.colors.primaryText
                            )
                            Text(
                                modifier = Modifier.padding(end = 24.dp),
                                text = visit.price.toString(),
                                color = ClassJournalTheme.colors.primaryText
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(top = 12.dp),
                            thickness = Dp.Hairline,
                            color = ClassJournalTheme.colors.controlColor
                        )
                    }
                }
            }
        }
    }
}