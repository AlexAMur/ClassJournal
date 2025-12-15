package com.catshome.classJournal.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.catshome.classJournal.ClassJournalCorners
import com.catshome.classJournal.ClassJournalImage
import com.catshome.classJournal.ClassJournalShape
import com.catshome.classJournal.ClassJournalSize
import com.catshome.classJournal.ClassJournalStyle
import com.catshome.classJournal.ClassJournalTypography
import com.catshome.classJournal.LocalClassJournalColors
import com.catshome.classJournal.LocalClassJournalImage
import com.catshome.classJournal.LocalClassJournalShape
import com.catshome.classJournal.LocalClassJournalTypography

@Composable
internal fun MainTheme(
    style: ClassJournalStyle = ClassJournalStyle.Purple,
    textSize: ClassJournalSize = ClassJournalSize.Medium,
    paddingSize: ClassJournalSize = ClassJournalSize.Medium,
    corners: ClassJournalCorners = ClassJournalCorners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = when (darkTheme) {
        true -> {
            when (style) {
                ClassJournalStyle.Purple -> purpleDarkPalette
                ClassJournalStyle.Blue -> blueDarkPalette
                ClassJournalStyle.Orange -> orangeDarkPalette
                ClassJournalStyle.Red -> redDarkPalette
                ClassJournalStyle.Green -> greenDarkPalette
            }
        }
        false -> {
            when (style) {
                ClassJournalStyle.Purple -> purpleLightPalette
                ClassJournalStyle.Blue -> blueLightPalette
                ClassJournalStyle.Orange -> orangeLightPalette
                ClassJournalStyle.Red -> redLightPalette
                ClassJournalStyle.Green -> greenLightPalette
            }
        }
    }

    val typography = ClassJournalTypography(
        heading = TextStyle(
            fontSize = when (textSize) {
                ClassJournalSize.Small -> 24.sp
                ClassJournalSize.Medium -> 28.sp
                ClassJournalSize.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                ClassJournalSize.Small -> 14.sp
                ClassJournalSize.Medium -> 16.sp
                ClassJournalSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                ClassJournalSize.Small -> 18.sp
                ClassJournalSize.Medium -> 20.sp
                ClassJournalSize.Big -> 22.sp
            },
            fontWeight = FontWeight.SemiBold
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                ClassJournalSize.Small -> 10.sp
                ClassJournalSize.Medium -> 12.sp
                ClassJournalSize.Big -> 14.sp
            }
        ),
       small = TextStyle(
                fontSize = when (textSize) {
                    ClassJournalSize.Small -> 8.sp
                    ClassJournalSize.Medium -> 10.sp
                    ClassJournalSize.Big -> 12.sp
                },
        fontWeight = FontWeight.Normal
    ),
    )

    val shapes = ClassJournalShape(
        padding = when (paddingSize) {
            ClassJournalSize.Small -> 12.dp
            ClassJournalSize.Medium -> 16.dp
            ClassJournalSize.Big -> 20.dp
        },
        cornersStyle = when (corners) {
            ClassJournalCorners.Flat -> RoundedCornerShape(0.dp)
            ClassJournalCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    val images = ClassJournalImage(
        mainIcon =  null, //if (darkTheme) R.drawable.ic_baseline_mood_24 else R.drawable.ic_baseline_mood_bad_24,
        mainIconDescription = if (darkTheme) "Good Mood" else "Bad Mood"
    )

    CompositionLocalProvider(
        LocalClassJournalColors provides colors,
        LocalClassJournalTypography provides typography,
        LocalClassJournalShape provides shapes,
        LocalClassJournalImage provides images,
        content = content
    )
}