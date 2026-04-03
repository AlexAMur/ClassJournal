package com.catshome.classJournal.screens.Visit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.communs.TextField

@Composable
fun ItemVisitContent(
    fio: String,
    price: String,
    onValueChange: (String) -> Unit,
    errorState: Boolean = false,
    isChecked: Boolean = false,
    onCheckClick: () -> Unit
) {
        Column(
            Modifier.padding(start = 8.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fio,
                    label = stringResource(R.string.FIO),
                    supportingText = null,
                    onValueChange = onValueChange,
                    trailingIcon = null,
                    minLines = 1,
                    singleLine = true,
                    errorState = errorState,
                    readOnly = false
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier,
                    value = price,
                    label = stringResource(R.string.visit_price),
                    supportingText = null,
                    onValueChange = onValueChange,
                    trailingIcon = null,
                    minLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.merge(
                        KeyboardOptions(keyboardType = KeyboardType.Number)
                    ),
                    errorState = errorState,
                    readOnly = false
                )
                Icon(
                    painter =
                        if (isChecked)
                            painterResource(R.drawable.box_ckeck)
                        else
                            painterResource(R.drawable.box_out),
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )
            }
        }
   // }
}