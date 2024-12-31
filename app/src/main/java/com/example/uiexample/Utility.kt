package com.example.uiexample

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator

// Utility function to get window height in dp
@Composable
fun getWindowHeightInDp(): Dp {
    val context = LocalContext.current
    val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
    val heightPx = metrics.bounds.height() // Get height in pixels
    val density = LocalDensity.current.density // Get the device density
    return (heightPx / density).dp // Convert px to dp
}