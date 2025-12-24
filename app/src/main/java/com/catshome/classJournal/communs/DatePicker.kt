package com.catshome.classJournal.communs

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.toDateRu
import com.catshome.classJournal.domain.communs.toLocalDateTime
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import java.time.ZoneOffset
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerFieldToModal(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    supportText: String?= null,
    onDateSelected: (Long?) -> Unit
) {


        var date by remember { mutableStateOf(
            if (value.length == DATE_FORMAT_RU.length)
            value.toDateRu()?.time
            else
            value.toLocalDateTime()?.toLong()
        ) }
    var showModal by rememberSaveable { mutableStateOf(false) }
    //val datePickerState = rememberDatePickerState()
    //var t =  convertMillisToDate(selectedDate?:0)?:""

    TextField(
        value = value,
        label = label,
        supportingText = supportText,
        modifier = modifier,
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showModal = !showModal }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date",
                    tint = ClassJournalTheme.colors.controlColor
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.merge(KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    )
    if (showModal) {
           DatePickerModal(
               inicialDate = Date.from(date?.toLocalDateTimeRu()?.toInstant(ZoneOffset.UTC))?: Date(),//Date.from(date.toLocalDateTime()?.toInstant(ZoneOffset.UTC))?: Date(),
            onDateSelected = onDateSelected,
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    inicialDate: Date,// = Date(),
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerColors = DatePickerDefaults.colors(
        containerColor = ClassJournalTheme.colors.primaryBackground,
        titleContentColor = ClassJournalTheme.colors.primaryText,
        headlineContentColor = ClassJournalTheme.colors.primaryText,
        weekdayContentColor = ClassJournalTheme.colors.primaryText,
        subheadContentColor = ClassJournalTheme.colors.primaryText,
        navigationContentColor = ClassJournalTheme.colors.primaryText,
        yearContentColor = ClassJournalTheme.colors.primaryText,
        //disabledYearContentColor = ClassJournalTheme.colors.primaryText,
        currentYearContentColor = ClassJournalTheme.colors.primaryText,
        selectedYearContentColor = ClassJournalTheme.colors.primaryText,
        disabledSelectedYearContentColor = ClassJournalTheme.colors.primaryText,
        selectedYearContainerColor = ClassJournalTheme.colors.tintColor,
        //disabledSelectedYearContainerColor = ClassJournalTheme.colors.primaryText,
        dayContentColor = ClassJournalTheme.colors.primaryText,
        disabledDayContentColor = ClassJournalTheme.colors.primaryText,
        selectedDayContentColor = ClassJournalTheme.colors.primaryText,
        //disabledSelectedDayContentColor = ClassJournalTheme.colors.primaryText,
        selectedDayContainerColor = ClassJournalTheme.colors.tintColor,
        //disabledSelectedDayContainerColor = ClassJournalTheme.colors.primaryText,
        todayContentColor = ClassJournalTheme.colors.tintColor,
        todayDateBorderColor = ClassJournalTheme.colors.tintColor,
        dayInSelectionRangeContainerColor = ClassJournalTheme.colors.tintColor,
        dayInSelectionRangeContentColor = ClassJournalTheme.colors.tintColor,
        dividerColor = ClassJournalTheme.colors.tintColor,
        dateTextFieldColors = TextFieldDefaults.colors(
            focusedTextColor = ClassJournalTheme.colors.primaryText,
            unfocusedTextColor = ClassJournalTheme.colors.primaryText,
            disabledTextColor = ClassJournalTheme.colors.primaryText,
            errorTextColor = ClassJournalTheme.colors.errorColor,
            focusedContainerColor = ClassJournalTheme.colors.primaryBackground,
            unfocusedContainerColor = ClassJournalTheme.colors.primaryBackground,
            disabledContainerColor = ClassJournalTheme.colors.primaryBackground,
            errorContainerColor = ClassJournalTheme.colors.primaryBackground,
            cursorColor = ClassJournalTheme.colors.tintColor,
            errorCursorColor = ClassJournalTheme.colors.errorColor,
            //selectionColors= ClassJournalTheme.colors.primaryText,
            focusedIndicatorColor = ClassJournalTheme.colors.primaryText,
            unfocusedIndicatorColor = ClassJournalTheme.colors.primaryText,
            disabledIndicatorColor = ClassJournalTheme.colors.primaryText,
            errorIndicatorColor = ClassJournalTheme.colors.primaryText,
            focusedLeadingIconColor = ClassJournalTheme.colors.primaryText,
            unfocusedLeadingIconColor = ClassJournalTheme.colors.primaryText,
            disabledLeadingIconColor = ClassJournalTheme.colors.primaryText,
            errorLeadingIconColor = ClassJournalTheme.colors.primaryText,
            focusedTrailingIconColor = ClassJournalTheme.colors.primaryText,
            unfocusedTrailingIconColor = ClassJournalTheme.colors.primaryText,
            disabledTrailingIconColor = ClassJournalTheme.colors.primaryText,
            errorTrailingIconColor = ClassJournalTheme.colors.primaryText,
            focusedLabelColor = ClassJournalTheme.colors.tintColor,
            unfocusedLabelColor = ClassJournalTheme.colors.primaryText,
            disabledLabelColor = ClassJournalTheme.colors.primaryText,
            errorLabelColor = ClassJournalTheme.colors.errorColor,
            focusedPlaceholderColor = ClassJournalTheme.colors.primaryText,
            unfocusedPlaceholderColor = ClassJournalTheme.colors.primaryText,
            disabledPlaceholderColor = ClassJournalTheme.colors.primaryText,
            errorPlaceholderColor = ClassJournalTheme.colors.primaryText,
            focusedSupportingTextColor = ClassJournalTheme.colors.primaryText,
            unfocusedSupportingTextColor = ClassJournalTheme.colors.primaryText,
            disabledSupportingTextColor = ClassJournalTheme.colors.primaryText,
            errorSupportingTextColor = ClassJournalTheme.colors.primaryText,
            focusedPrefixColor = ClassJournalTheme.colors.tintColor,
            unfocusedPrefixColor = ClassJournalTheme.colors.primaryText,
            disabledPrefixColor = ClassJournalTheme.colors.primaryText,
            errorPrefixColor = ClassJournalTheme.colors.errorColor,
            focusedSuffixColor = ClassJournalTheme.colors.tintColor,
            unfocusedSuffixColor = ClassJournalTheme.colors.primaryText,
            disabledSuffixColor = ClassJournalTheme.colors.primaryText,
            errorSuffixColor = ClassJournalTheme.colors.errorColor
        )
    )


    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = inicialDate.time)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.verticalScroll(state = rememberScrollState()),
        shape = ClassJournalTheme.shapes.cornersStyle,
        colors = datePickerColors,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }) {
                Text("OK", color = ClassJournalTheme.colors.tintColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = ClassJournalTheme.colors.tintColor)
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = datePickerColors
        )
    }
}