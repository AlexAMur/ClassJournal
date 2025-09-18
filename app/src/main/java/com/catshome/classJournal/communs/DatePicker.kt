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
import com.catshome.classJournal.screens.child.NewChildState
import com.catshome.classJournal.screens.child.TextField
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerFieldToModal(modifier: Modifier = Modifier, state: NewChildState, label: String) {


    //var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by rememberSaveable { mutableStateOf(false) }
    //val datePickerState = rememberDatePickerState()
    //var t =  convertMillisToDate(selectedDate?:0)?:""



    TextField(
        value = state.child.birthday, label = label,
        supportingText = label,
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
        keyboardOptions = KeyboardOptions.Default.merge(KeyboardOptions(keyboardType = KeyboardType.Number))

        )

    if (showModal) {
        DatePickerModal(
            onDateSelected = {

                state.child.birthday= it?.let {
                    convertMillisToDate(it)
                }?:""
            },
            onDismiss = { showModal = false }
        )
    }
}
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
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
        dividerColor = ClassJournalTheme.colors.primaryText,
        // dateTextFieldColors = ClassJournalTheme.colors.primaryText

    )
    val datePickerState = rememberDatePickerState()

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
        DatePicker(state = datePickerState,
        colors = datePickerColors)
    }
}