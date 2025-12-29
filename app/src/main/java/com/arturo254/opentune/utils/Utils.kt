package com.arturo254.opentune.utils

import android.graphics.Color as AndroidColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun reportException(throwable: Throwable) {
    throwable.printStackTrace()
}


@RequiresApi(Build.VERSION_CODES.O)
fun Color.invertValue(): Color {
    val argb = this.toArgb()
    val hsv = FloatArray(3)
    AndroidColor.colorToHSV(argb, hsv)

    // Invert the value (brightness) component
    hsv[2] = 1f - hsv[2]

    val newArgb = AndroidColor.HSVToColor(
        (alpha * 255).toInt(), // Preserve alpha
        hsv
    )
    return Color(newArgb)
}

// Extension property
val Color.invertedValue: Color
    get() = this.invertValue()