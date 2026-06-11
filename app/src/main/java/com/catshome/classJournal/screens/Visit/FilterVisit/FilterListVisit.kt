package com.catshome.classJournal.screens.Visit.FilterVisit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.domain.communs.FormatDate
import com.catshome.classJournal.domain.communs.formatRu
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.PayList.PayListEvent
import com.catshome.classJournal.screens.Visit.ListVisit.VisitListEvent
import com.catshome.classJournal.screens.Visit.ListVisit.VisitListViewModel

@Composable
fun FilterListVisitHeider(
    visitListViewModel: VisitListViewModel
) {
    val viewState by visitListViewModel.viewState().collectAsState()
    Column(
        Modifier
            .fillMaxWidth()
            .background(ClassJournalTheme.colors.primaryBackground)
            .padding(0.dp)
            .clickable {
                visitListViewModel.obtainEvent(VisitListEvent.onCollapse(true))
            }
    )
    {
        viewState.selectChild?.let { child ->
            if (child.fio.isNotEmpty())
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp),
                    color = ClassJournalTheme.colors.tintColor,
                    text = child.fio
                )
        }
        Row(
            Modifier
                .background(ClassJournalTheme.colors.primaryBackground)
                .fillMaxWidth()
                ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (viewState.beginDate == null && viewState.endDate == null) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    color = ClassJournalTheme.colors.tintColor,
                    text = "за ${stringResource(R.string.filter_all)} время."
                )
            } else {

                viewState.beginDate?.formatRu(format = FormatDate.Date)?.let {
                    if (it.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            color = ClassJournalTheme.colors.tintColor,
                            text = "c ${it}"
                        )
                    }
                }
                viewState.endDate.let {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f),
                        color = ClassJournalTheme.colors.tintColor,
                        text = if (!viewState.endDate?.formatRu(FormatDate.Date).isNullOrEmpty())
                            " по ${viewState.endDate?.formatRu(FormatDate.Date)}"
                        else
                            "${stringResource(R.string.filter_all)} время"
                    )

                }
            }
            Icon(
                modifier = Modifier,
                painter = painterResource(R.drawable.arrow_drop_down_48),
                contentDescription = "",
                tint = ClassJournalTheme.colors.tintColor
            )
        }
    }
}
