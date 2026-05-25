package com.catshome.classJournal.communs

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalColors
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun DualCanvasProgressBar(
    value1: Float,
    value2: Float,
    colorMain: Color = Color(0xFF6200EE),
    colorSecondary: Color = Color(0xFFBB86FC),
    colorBackground: Color = Color(0xFFEFEFEF),
    modifier: Modifier = Modifier
) {
    val radius = 16f
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {
            val barHeight = size.height
            val width = size.width

            // Отрисовка фона
            drawRoundRect(
                color = colorBackground,
                size = Size(width, barHeight),
                cornerRadius = CornerRadius(x = radius, y = radius)
            )

            // Отрисовка второго бара (базового)
            val width1 = size.width * value1.coerceIn(0f, 1f)-radius
            val width2 = size.width * value2.coerceIn(0f, 1f)-radius
            // Отрисовка первого бара (основного) поверх
            val path= Path().apply{
                moveTo(radius/2,0f)
                lineTo(width1, 0f )
                arcTo(
                    rect = Rect(
                        Offset(width1,  0f),
                        Size(radius, radius)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )
                lineTo(x = width1,y = barHeight/2)
                lineTo(x = 0f,y = barHeight/2)
                arcTo(
                    rect = Rect(
                          Offset(0f,  0f),
                        Size(radius, radius / 2)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
                moveTo(x = 0f,y = barHeight/2)
                lineTo(x = 0f,y = radius/4)
                lineTo(x = radius/2, y = 0f)
                close()
            }
            drawPath(path = path, color = colorMain)
            // Отрисовка второго бара (базового)
//            drawRoundRect(
//                color = colorSecondary,
//                topLeft = Offset(x = 0f, y = barHeight / 2),
//                size = Size(width * value2.coerceIn(0f, 1f), barHeight / 2),
//                cornerRadius = CornerRadius(x = 8f, y = 8f)
//            )

            val path2= Path().apply{
                moveTo(0f,barHeight/2)
                lineTo(width2, barHeight/2)
                arcTo(
                    rect = Rect(
                        Offset(width2,  barHeight/2),
                        Size(radius, barHeight/2)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )
                lineTo(x = width2,y = barHeight)
                lineTo(x = radius,y = barHeight)
                arcTo(
                    rect = Rect(
                        Offset(0f,  barHeight/2),
                        Size(barHeight/2, barHeight/2 )
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                moveTo(x = 0f,y = barHeight)
                lineTo(x = 0f,y = barHeight-radius)

                close()
            }

        drawPath(path = path2, color = colorSecondary)
        }

}