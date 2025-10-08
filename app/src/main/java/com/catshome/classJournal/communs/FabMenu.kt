package com.catshome.classJournal.communs


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus


@Composable
fun fabMenu(
    listFAB: List<ItemFAB>,
    modifier: Modifier = Modifier
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    val bottomPadding =
        LocalSettingsEventBus.current.currentSettings.collectAsState().value.innerPadding.calculateBottomPadding()
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
            listFAB.forEach { itemFAB->
                if (showMenu) {
                    FloatingActionButton(
                        modifier = itemFAB.modifier.padding(end = 16.dp),
                        containerColor = ClassJournalTheme.colors.tintColor,
                        contentColor = ClassJournalTheme.colors.primaryText,
                        onClick = {
                            itemFAB.onClick()
                            showMenu = false
                        }) {
                        Row {
                            Icon(
                                painter = (itemFAB.icon?:Icons.Default.Info) as Painter,
                                null,
                                modifier = Modifier.padding(start = 16.dp , end = 16.dp),
                                tint = ClassJournalTheme.colors.primaryBackground
                            )
                           // Text(itemFAB.label, modifier = Modifier.padding(start = 16.dp))
                        }
                    }
                }
            }
            Spacer(Modifier.padding(bottom = 8.dp))
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = bottomPadding + 16.dp, end = 16.dp)
                    .onFocusChanged{if(!it.isFocused) showMenu =false},
                containerColor = if (showMenu) ClassJournalTheme.colors.controlColor else ClassJournalTheme.colors.tintColor,
                contentColor = if (showMenu) ClassJournalTheme.colors.primaryText else
                    ClassJournalTheme.colors.secondaryBackground,
                shape = if(showMenu) CircleShape else FloatingActionButtonDefaults.shape,

                onClick = {
                    showMenu = !showMenu
                })
            {
                Icon(
                    if (showMenu) Icons.Sharp.Close else Icons.Sharp.Add, null,
                    tint = if (showMenu) ClassJournalTheme.colors.primaryText else
                        ClassJournalTheme.colors.secondaryBackground
                )
            }
        }
    }
}