package com.catshome.classJournal.communs.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R

@Composable
fun ItemWithCheck(
    startImage: Painter?,
    onClick:(String)->Unit,
    item: String,
    isChecked: Boolean,
    texStyle: TextStyle? =  null
    ){
    Card(
        shape = ClassJournalTheme.shapes.cornersStyle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    //выбор элемента
                    onClick(item)
                    //viewModel.obtainEvent(NewChildEvent.SelectGroup(item.group?.uid.toString()))
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val modifier = Modifier.padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 8.dp,

            )
            startImage?.let {
                Icon(
                    painter = it,
                    modifier = modifier.widthIn(min = 56.dp)
                        ,
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )
            }
            Text(
                text = item,
                modifier = modifier.fillMaxWidth()
                    .weight(1f),
                style = texStyle?:ClassJournalTheme.typography.body,
                color = ClassJournalTheme.colors.primaryText
            )

            if (isChecked) {
                Icon(
                    painter = painterResource(R.drawable.box_ckeck),
                    modifier = modifier.widthIn(min = 56.dp),
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )
            } else {

                Icon(
                    painter = painterResource(R.drawable.box_out),
                    modifier = modifier.widthIn(min = 56.dp),
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )
            }

        }
    }
}