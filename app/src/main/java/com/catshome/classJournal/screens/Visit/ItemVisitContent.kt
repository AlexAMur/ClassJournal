package com.catshome.classJournal.screens.Visit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.catshome.classJournal.communs.SearchField
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.communs.TimePikerDialog
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.screens.PayList.ItemChildInSearch
import com.catshome.classJournal.screens.PayList.NewPayEvent
import java.time.ZonedDateTime
import java.util.Date
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemVisitContent(
    fio: String,
    date: Date,
    price: String,
    listChild: List<MiniChild>? = null,
    isShowDateDialog: Boolean,
    onValueChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    isSearchError: Boolean = false,
    isPriceError: Boolean = false,
    errorSearchMessage: String = "",
    showDateDialog: (Boolean) -> Unit,
    onDateSelect: (Long?) -> Unit,
    onClearSelect: () -> Unit,
    onSelectChild: (MiniChild) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        SearchField(
            modifier = Modifier.fillMaxWidth(),
            text = fio,
            label = stringResource(R.string.FIO),
            isError = isSearchError,
            errorMessage = errorSearchMessage,
            onClickCancel = onClearSelect,
            onSearch = onValueChange
        )

        listChild?.let { listChild ->
            if (listChild.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(start = 16.dp, end = 16.dp)
                        .offset(y = (-19).dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ClassJournalTheme.colors.secondaryBackground,
                        contentColor = ClassJournalTheme.colors.primaryText
                    ),
                    border = BorderStroke(2.dp, color = ClassJournalTheme.colors.tintColor),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 8.dp)
                            .heightIn(min = 0.dp, max = 300.dp)
                    ) {
                        itemsIndexed(listChild) { index, child ->
                            ItemChildInSearch(
                                name = child.name,
                                surname = child.surname,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        top = 4.dp,
                                        end = 16.dp,
                                        bottom = 8.dp
                                    ),
                                style = ClassJournalTheme.typography.body,
                                contentColor = ClassJournalTheme.colors.tintColor,
                                onClicked = {
                                    if (child.uid.isNotEmpty())
                                        onSelectChild(child)
                                },
                            )
                        }
                    }
                }
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .clickable(onClick = {
                    showDateDialog(true)
                }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.visit_date),
                color = ClassJournalTheme.colors.primaryText,
                fontStyle = ClassJournalTheme.typography.toolbar.fontStyle,
                modifier = Modifier.padding(end = 16.dp)
            )
            date.time.toLocalDateTimeRuString()?.let {
                Text(
                    fontStyle = ClassJournalTheme.typography.toolbar.fontStyle,
                    text = it,
                    color = ClassJournalTheme.colors.primaryText,
                    modifier = Modifier,
                )
            }
            Icon(
                modifier = Modifier
                    .padding(start = 24.dp),
                painter = painterResource(R.drawable.calendar_month_24),
                contentDescription = null,
                tint = ClassJournalTheme.colors.tintColor
            )
        }

        TextField(
            modifier = Modifier,
            value = price,
            label = stringResource(R.string.visit_price),
            supportingText = null,
            onValueChange = onPriceChange,
            trailingIcon = null,
            minLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.merge(
                KeyboardOptions(keyboardType = KeyboardType.Number)
            ),
            errorState = isPriceError,
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