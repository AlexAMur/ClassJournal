package com.catshome.classJournal.communs.mainMenu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CardColors
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.catshome.classJournal.ClassJournalTheme
import kotlinx.datetime.format.Padding

@Composable
fun MainModalNavDrawer(
    padding: PaddingValues,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            drawerContent(
                paidding = padding,
                colorsCard = CardColors(
                    containerColor = ClassJournalTheme.colors.primaryBackground,
                    contentColor = ClassJournalTheme.colors.primaryText,
                    disabledContainerColor = ClassJournalTheme.colors.primaryBackground,
                    disabledContentColor = ClassJournalTheme.colors.primaryText
                ),
                onClick = {}
            )
        },
        modifier = Modifier,
        drawerState = drawerState,
        scrimColor = DrawerDefaults.scrimColor, //ClassJournalTheme.colors.secondaryBackground,
        content = content
    )
}