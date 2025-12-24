package com.catshome.classJournal.communs.FilterScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R

@Composable
fun radioOptionSorting(value: String, optionList: List<String>, onOptionSelected:(String)-> Unit){//, onSelected:(String, Int)->Unit) {

    Card(
        Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ClassJournalTheme.colors.primaryBackground,
            contentColor = ClassJournalTheme.colors.primaryText
        )
    ) {
        Column {
            Text(
                text = stringResource(R.string.filter_sort),
                Modifier.padding(16.dp),
                fontSize = ClassJournalTheme.typography.body.fontSize
                )
            optionList.forEachIndexed {index, text->

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 16.dp)
                        .selectable(
                            selected = (text == value),
                            onClick = {onOptionSelected(text)},// onOptionSelected(text) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(

                        colors = RadioButtonDefaults.colors(
                            ClassJournalTheme.colors.tintColor
                        ),
                        selected = (text == value),
                        onClick = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = text,
                        color = ClassJournalTheme.colors.primaryText
                    )
                }
            }
        }
    }
}