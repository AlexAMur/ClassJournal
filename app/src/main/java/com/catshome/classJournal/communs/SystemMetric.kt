package com.catshome.classJournal.communs

import android.content.Context
import android.os.Build
import android.os.Build.VERSION.SDK
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getHeightSizeDp(context: Context): Dp {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
        return windowManager.currentWindowMetrics.windowInsets.frame.height.dp
    } else {
        val insets = WindowInsets.safeDrawing.asPaddingValues()
        return context.resources.displayMetrics.heightPixels.dp
    }
}
