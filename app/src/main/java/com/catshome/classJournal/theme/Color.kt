package com.catshome.classJournal.theme

import androidx.compose.ui.graphics.Color
import com.catshome.classJournal.ClassJournalColors

internal val baseLightPalette = ClassJournalColors(
    primaryBackground = Color(0xFFFFFFFF),
    primaryText = Color(0xFF3D454C),
    secondaryBackground = Color(0xFFF3F4F5),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color.Magenta,
    controlColor = Color(0xFF7A8A99),
    errorColor = Color(0xFFEA1A1A),
    disableColor = Color.DarkGray,
    disableContentColor = Color.LightGray
)

internal val baseDarkPalette = ClassJournalColors(
    primaryBackground = Color(0xFF23282D),
    primaryText = Color(0xFFF2F4F5),
    secondaryBackground = Color(0xFF191E23),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color.Magenta,
    controlColor = Color(0xFF7A8A99),
    errorColor = Color(0xFFEA1A1A),
    disableColor = Color.DarkGray,
    disableContentColor = Color.LightGray
)

val purpleLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFAD57D9)
)

val purpleDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFD580FF)
)

val orangeLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFFF6619)
)

val orangeDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFFF974D)
)

val blueLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF4D88FF)
)

val blueDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF99BBFF)
)

val redLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFE63956)
)

val redDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFFF5975)
)

val greenLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF12B37D)
)

val greenDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF7EE6C3)
)





/*
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

 */