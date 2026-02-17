package com.catshome.classJournal.communs

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePikerDialog(
    title: String,
    context: Context,
    onDismiss: () -> Unit,
    onConfirm: (TimePickerState, Int) -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val currentTime = Calendar.getInstance()
    var duration by rememberSaveable { mutableStateOf("40") }
    var support by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = ClassJournalTheme.shapes.cornersStyle,
            tonalElevation = 6.dp,
            modifier =
                Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = ClassJournalTheme.shapes.cornersStyle,
                        color = ClassJournalTheme.colors.secondaryBackground
                    ),
            color = ClassJournalTheme.colors.secondaryBackground
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
                    .background(ClassJournalTheme.colors.secondaryBackground),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = ClassJournalTheme.typography.body
                )

                toggle()
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        containerColor = ClassJournalTheme.colors.primaryText,
                        clockDialColor = ClassJournalTheme.colors.secondaryBackground,
                        clockDialSelectedContentColor = ClassJournalTheme.colors.primaryText,
                        clockDialUnselectedContentColor = ClassJournalTheme.colors.primaryText,
                        selectorColor = ClassJournalTheme.colors.tintColor,
                        periodSelectorBorderColor = ClassJournalTheme.colors.primaryBackground,
                        periodSelectorSelectedContainerColor = ClassJournalTheme.colors.tintColor,
                        periodSelectorUnselectedContainerColor = ClassJournalTheme.colors.controlColor,
                        periodSelectorSelectedContentColor = ClassJournalTheme.colors.secondaryBackground,
                        periodSelectorUnselectedContentColor = ClassJournalTheme.colors.tintColor,
                        timeSelectorSelectedContainerColor = ClassJournalTheme.colors.tintColor,
                        timeSelectorUnselectedContainerColor = ClassJournalTheme.colors.controlColor,
                        timeSelectorSelectedContentColor = ClassJournalTheme.colors.primaryText,
                        timeSelectorUnselectedContentColor = ClassJournalTheme.colors.primaryText
                    )

                )
                TextField(
                    value = duration,
                    label = "Продолжительность занятия",
                    onValueChange = {
                        duration = it
                        error = false
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    supportingText = support,
                    errorState = error,
                    // colors = if (error.value) ClassJournalTheme.colors.errorColor else ClassJournalTheme.colors.tintColor,
                    modifier = Modifier
                )

                Row(
                    modifier = Modifier
                    //height(40.dp)
                    // .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onDismiss,

                        ) {
                        Text(
                            stringResource(R.string.bottom_cancel),
                            color = ClassJournalTheme.colors.tintColor
                        )
                    }
                    TextButton(
                        onClick = {
                            try {
                                if (duration.toInt() < 1)
                                    throw IllegalArgumentException(context.getString(R.string.error_zero_value))
                                onConfirm(timePickerState, duration.toInt())
                            } catch (e: Exception) {
                                error = true
                                if (e is NumberFormatException) {

                                    support = context.getString(R.string.error_invalid_value)
                                } else support = e.message ?: "Ошибка."
                            }
                        },

                        ) {
                        Text(
                            stringResource(R.string.ok),
                            color = ClassJournalTheme.colors.tintColor
                        )
                    }
                }
            }
        }
    }
}