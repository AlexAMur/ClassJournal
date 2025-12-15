package com.catshome.classJournal.screens.PayList

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.view.TintableBackgroundView
import androidx.wear.compose.material.Text
import com.catshome.classJournal.ClassJournalTheme


@Composable
fun ItemChildInSearch(
    name: String,
    surname: String,
    modifier: Modifier,
    style: TextStyle,
    onClicked: ()->Unit,
    contentColor: Color = ClassJournalTheme.colors.primaryText
){
    Row(modifier = modifier.
                    clickable(onClick =  onClicked)) {
        Text(text = name, color= contentColor, style = style)
        Text(text = surname,modifier= Modifier.padding(start = 16.dp),
            style = style , color= contentColor)
    }

}