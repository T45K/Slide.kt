package io.github.t45k.slidekt.engine

import androidx.compose.ui.unit.DpSize
import io.github.t45k.slidekt.api.AspectRatio

fun calcSlideSize(dpSize: DpSize, aspectRatio: AspectRatio): DpSize {
    val (width, height) = dpSize
    return if (width > height * aspectRatio.ratio) {
        DpSize(height * aspectRatio.ratio, height)
    } else {
        DpSize(width, width / aspectRatio.ratio)
    }
}
