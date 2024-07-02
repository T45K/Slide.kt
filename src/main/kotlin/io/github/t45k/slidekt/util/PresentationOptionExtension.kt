package io.github.t45k.slidekt.util

import androidx.compose.ui.graphics.Color
import io.github.t45k.slidekt.api.PresentationOption

internal fun PresentationOption.bgColor() = if (darkMode) Color.Black else Color.White
internal fun PresentationOption.textColor() = if (darkMode) Color.White else Color.Black
