package com.catshome.classJournal.screens.PayList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.resource.R
import kotlin.math.absoluteValue

@Composable
fun itemPay(
    fio: String,
    offset: Offset,
    date: String,
    payment: String,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .background(ClassJournalTheme.colors.secondaryBackground)
            .fillMaxSize()
            .clickable(onClick = onClick)
            .offset(offset.x.dp, y = 0.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.75f)
                .offset(x = offset.x.absoluteValue.dp)
        ) {
            Text(
                fio,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)

                    .fillMaxWidth(),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.body
            )

            Text(
                stringResource(R.string.paymant_date) + " $date",
                modifier = Modifier
                    .padding(start = 16.dp)
                    //.offset(x=offset.x.unaryPlus().dp)
                    .fillMaxWidth(),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.caption
            )

        }
        Text(
            "${payment} p.",
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .offset(x = (offset.x).absoluteValue.dp)
                .fillMaxWidth(),
            color = ClassJournalTheme.colors.primaryText,
            style = ClassJournalTheme.typography.body
        )
    }
    //}
}
