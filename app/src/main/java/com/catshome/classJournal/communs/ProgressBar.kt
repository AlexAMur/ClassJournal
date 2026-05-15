package com.catshome.classJournal.communs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DualCanvasProgressBar(
    value1: Float,
    value2: Float,
    colorMain: Color = Color(0xFF6200EE),
    colorSecondary: Color = Color(0xFFBB86FC),
    colorBackground: Color = Color(0xFFEFEFEF),

    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
    ) {
        val barHeight = size.height
        val width = size.width

        // Отрисовка фона
        drawRect(
            color = colorBackground,
            size = Size(width, barHeight)
        )

        // Отрисовка второго бара (базового)
        drawRect(
            color = colorSecondary,
            size = Size(width * value2.coerceIn(0f, 1f), barHeight)
        )

        // Отрисовка первого бара (основного) поверх
        drawRect(
            color = colorMain,
            size = Size(width * value1.coerceIn(0f, 1f), barHeight)
        )
    }
}