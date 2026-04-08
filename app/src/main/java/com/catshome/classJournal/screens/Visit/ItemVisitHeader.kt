package com.catshome.classJournal.screens.Visit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.resource.R

@Composable
fun ItemVisitHeader(
    modifier: Modifier = Modifier,
    cardColor: CardColors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor),
    textColor: Color = ClassJournalTheme.colors.primaryText,
    header: String,
    isChecked: Boolean = false,
    onClick:()->Unit
) {
    Card(
        modifier =  Modifier
            .fillMaxWidth(),
        colors = cardColor,
        shape = RoundedCornerShape(0.dp),
    )
    {
        Row(
            Modifier.fillMaxWidth()
                .padding(start = 16.dp , end = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(0.9f),
                text = header,
                color = textColor
            )
                Icon(
                    painter =
                        painterResource(if (isChecked)
                            R.drawable.box_ckeck
                        else
                            R.drawable.box_out),
                    modifier = modifier
                        .clickable(onClick = onClick),
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )

        }
    }
}