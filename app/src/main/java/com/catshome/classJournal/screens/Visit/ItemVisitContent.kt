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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.catshome.classJournal.communs.DatePickerModal
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.communs.TimePikerDialog
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.domain.communs.toLong
import java.time.ZonedDateTime
import java.util.Date
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemVisitContent(
    fio: String,
    date: Date,
    price: String,
    isShowDateDialog: Boolean,
    onValueChange: (String) -> Unit,
    errorState: Boolean = false,
    showDateDialog: (Boolean) -> Unit,
    onDateSelect: (Long?) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
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
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .clickable(onClick = {
                    showDateDialog(true)
                }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            date.time.toLocalDateTimeRuString()?.let {
                Text(
                    fontStyle = ClassJournalTheme.typography.toolbar.fontStyle,
                    text = it,
                    color = ClassJournalTheme.colors.primaryBackground,
                    modifier = Modifier.weight(0.9f),
                )
            }
//            Icon(
//                modifier = Modifier.weight(0.3f),
//                painter = painterResource(R.drawable.calendar_today_24),
//                contentDescription = null,
//                tint = ClassJournalTheme.colors.tintColor
//            )
        }

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
    }
    if (isShowDateDialog) {
        DatePickerModal(
            inicialDate = date,
            onDateSelected = onDateSelect,
            onDismiss = {
                showDateDialog(false)
            }
        )
    }

}