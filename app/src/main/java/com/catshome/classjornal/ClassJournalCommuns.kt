package com.catshome.ClassJournal.com.catshome.classjornal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class ClassJournalColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color,
)

data class ClassJournalTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

data class ClassJournalShape(
    val padding: Dp,
    val cornersStyle: Shape
)

data class ClassJournalImage(
    val mainIcon: Int?,
    val mainIconDescription: String
)

object ClassJournalTheme {
    internal val colors: ClassJournalColors
        @Composable
        internal get() = LocalClassJournalColors.current

    internal val typography: ClassJournalTypography
        @Composable
        internal get() = LocalClassJournalTypography.current

    internal val shapes: ClassJournalShape
        @Composable
        internal get() = LocalClassJournalShape.current

    internal val images: ClassJournalImage
        @Composable
        internal get() = LocalClassJournalImage.current
}

enum class ClassJournalStyle {
    Purple, Orange, Blue, Red, Green
}

enum class ClassJournalSize {
    Small, Medium, Big
}

enum class ClassJournalCorners {
    Flat, Rounded
}

internal val LocalClassJournalColors = staticCompositionLocalOf<ClassJournalColors> {
    error("No colors provided")
}

internal val LocalClassJournalTypography = staticCompositionLocalOf<ClassJournalTypography> {
    error("No font provided")
}

internal val LocalClassJournalShape = staticCompositionLocalOf<ClassJournalShape> {
    error("No shapes provided")
}

internal val LocalClassJournalImage = staticCompositionLocalOf<ClassJournalImage> {
    error("No images provided")
}