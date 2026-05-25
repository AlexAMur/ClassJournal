package com.catshome.classJournal.screens.PayList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.communs.DualCanvasProgressBar
import com.catshome.classJournal.resource.R

@Composable
fun StatisticPayCard(incomePerMonth: Int, lastMonth: Int, totalYear: Int) {
    val stoProcentov: Float =lastMonth.toFloat()
    Card(
        Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ClassJournalTheme.colors.secondaryBackground,
            contentColor = ClassJournalTheme.colors.primaryText
        ),
        shape = RoundedCornerShape(size = 24.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row() {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.pay_year),
                    fontStyle = ClassJournalTheme.typography.toolbar.fontStyle
                )
                Text(
                    modifier = Modifier.padding(start = 24.dp, bottom = 16.dp),
                    text = totalYear.toString(),
                    fontStyle = ClassJournalTheme.typography.heading.fontStyle
                )
            }
            Row() {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.pay_month),
                    fontStyle = ClassJournalTheme.typography.toolbar.fontStyle
                    )
                Text(
                    text = incomePerMonth.toString(),
                    modifier = Modifier.padding(start = 24.dp, bottom = 16.dp),
                    fontStyle = ClassJournalTheme.typography.heading.fontStyle
                )
            }
            DualCanvasProgressBar(
                value1 = incomePerMonth.toFloat() /stoProcentov,
                value2 = lastMonth.toFloat()  /stoProcentov,
                colorMain = ClassJournalTheme.colors.tintColor,
                colorSecondary = ClassJournalTheme.colors.controlColor,
                colorBackground = ClassJournalTheme.colors.disableContentColor,
                modifier = Modifier.padding(8.dp)
            )


        }
    }
}