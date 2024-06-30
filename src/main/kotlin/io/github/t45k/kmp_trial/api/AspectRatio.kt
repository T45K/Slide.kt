package io.github.t45k.kmp_trial.api

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

enum class AspectRatio(val ratio: Float) {
    RATIO_16_9(16f / 9f), RATIO_4_3(4f / 3f);

    fun isWidthLonger(dpSize: DpSize): Boolean =
        dpSize.width / dpSize.height < ratio
}
