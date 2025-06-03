package com.catshome.classjornal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.catshome.ClassJournal.com.catshome.classjornal.*

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
        )
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



/*private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ClassJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}*/