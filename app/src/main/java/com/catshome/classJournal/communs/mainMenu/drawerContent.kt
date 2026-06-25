package com.catshome.classJournal.communs.mainMenu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.resource.R

@Composable
fun drawerContent(paidding: PaddingValues,
                  colorsCard: CardColors,
                  onClickSettings: () -> Unit,
                  onClickBackup: () -> Unit,
                  onClickRestore: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize(0.9f)
            . padding(paidding),
        colors = colorsCard
    ) {
        NavigationDrawerItem(
            label = { Text(
                stringResource(R.string.imenu_settings),
                color = ClassJournalTheme.colors.primaryText
            ) },
            selected = false,
            icon = { Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                tint = ClassJournalTheme.colors.tintColor,
            ) },
            badge = {  }, // Placeholder
            onClick = {onClickSettings.invoke()}
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 8.dp),
            color = ClassJournalTheme.colors.disableContentColor
        )

        NavigationDrawerItem(
            label = { Text(
                stringResource(R.string.imenu_backup),
                color = ClassJournalTheme.colors.primaryText
            ) },
            selected = false,
            icon = { Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = ClassJournalTheme.colors.tintColor,
                ) },
            badge = {  }, // Placeholder
            onClick = onClickBackup
        )

        NavigationDrawerItem(
            label = { Text(
                stringResource(R.string.imenu_restore),
                color = ClassJournalTheme.colors.primaryText
            ) },
            selected = false,
            icon = { Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = null,
                tint = ClassJournalTheme.colors.errorColor,
            ) },
            badge = {  },
            onClick = onClickRestore
        )
    }
}