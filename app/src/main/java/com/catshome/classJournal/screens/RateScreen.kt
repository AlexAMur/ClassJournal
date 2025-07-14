package com.catshome.classJournal


import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun RateScreen() {

    val message = rememberSaveable { mutableStateOf("") }
    var cardColors by  remember {mutableStateOf(Color.Red)}

    var isErrorDisplay by remember { mutableStateOf(false) }

    cardColors= ClassJournalTheme.colors.secondaryBackground
    Column(
        modifier = Modifier.fillMaxSize()
            .background(ClassJournalTheme.colors.primaryBackground),
        verticalArrangement = Arrangement.Center
    ) {

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
                    .height(200.dp),
                shape = ClassJournalTheme.shapes.cornersStyle,
                colors = CardDefaults.cardColors(containerColor = ClassJournalTheme.colors.secondaryBackground)
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = message.value,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ClassJournalTheme.colors.primaryText,
                        unfocusedTextColor = ClassJournalTheme.colors.primaryText),
                    label = { Text(stringResource(R.string.name_group_label)) },
                    supportingText = {
                        Text(
                            text = stringResource(R.string.error_name_group),
//                                color = if (isErrorDisplay) ClassJournalTheme.colors.errorColor
//                                else ClassJournalTheme.colors.primaryText
                        )
                    },
                    onValueChange = { message.value = it }
                )

            }
        }
    }


