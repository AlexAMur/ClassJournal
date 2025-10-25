package com.catshome.classJournal.communs

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun ActionIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color = ClassJournalTheme.colors.errorColor,
    icon: ImageVector,
    contentDescription: String? = null,
    tint: Color = ClassJournalTheme.colors.primaryBackground
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
