package com.catshome.classJournal.communs.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R



@Composable
fun ItemWithCheck(
    item: String,
    isChecked: Boolean,
    onClick:(String)->Unit
){
    Card(
        shape = ClassJournalTheme.shapes.cornersStyle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    //выбор элемента
                    onClick(item.toString())
                    //viewModel.obtainEvent(NewChildEvent.SelectGroup(item.group?.uid.toString()))
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val modifier = Modifier.padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 8.dp,
                end = 16.dp
            )
            Text(
                text = item,
                modifier = modifier,
                style = ClassJournalTheme.typography.body,
                color = ClassJournalTheme.colors.primaryText
            )

            if (isChecked) {
                Icon(
                    painter = painterResource(R.drawable.box_ckeck),
                    modifier = modifier,
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )
            } else {

                Icon(
                    painter = painterResource(R.drawable.box_out),
                    modifier = modifier,
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )
            }

        }
    }
}