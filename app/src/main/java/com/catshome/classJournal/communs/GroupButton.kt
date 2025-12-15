package com.catshome.classJournal.communs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.Text
import com.catshome.classJournal.ClassJournalSize
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun GroupButton(contentList: List<String>,
                selectedOption: Int,
                colorScheme: SegmentedButtonColors = SegmentedButtonColors(
                    activeContainerColor = ClassJournalTheme.colors.tintColor,
                    activeContentColor = ClassJournalTheme.colors.primaryText,
                    inactiveContainerColor = ClassJournalTheme.colors.controlColor,
                    inactiveContentColor = ClassJournalTheme.colors.secondaryText,
                    activeBorderColor = ClassJournalTheme.colors.controlColor,
                    inactiveBorderColor =ClassJournalTheme.colors.controlColor,
                    disabledActiveContainerColor = ClassJournalTheme.colors.disableColor,
                disabledActiveContentColor = ClassJournalTheme.colors.disableColor,
                disabledActiveBorderColor = ClassJournalTheme.colors.disableColor,
                disabledInactiveContainerColor =ClassJournalTheme.colors.disableColor,
                disabledInactiveContentColor = ClassJournalTheme.colors.disableColor,
                disabledInactiveBorderColor = ClassJournalTheme.colors.disableColor,)
                ,
                onClick:(Int)->Unit) {
    SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)) {
        contentList.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = contentList.size,
                    baseShape = RoundedCornerShape(
                        topStart = CornerSize(ClassJournalTheme.shapes.padding),
                        bottomStart = CornerSize(ClassJournalTheme.shapes.padding),
                        topEnd = CornerSize(ClassJournalTheme.shapes.padding),
                        bottomEnd = CornerSize(ClassJournalTheme.shapes.padding))
                ),
                onClick = {onClick(index)},
                selected = index == selectedOption,
                colors = colorScheme
            ) {
                Text(
                    fontStyle = ClassJournalTheme.typography.caption.fontStyle,
                    fontSize = 10.sp,
                    color = ClassJournalTheme.colors.primaryText,
                    text = label
                )
            }
        }
    }
}