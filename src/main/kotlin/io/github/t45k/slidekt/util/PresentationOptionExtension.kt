package io.github.t45k.slidekt.util

import androidx.compose.ui.graphics.Color
import io.github.t45k.slidekt.api.PresentationOption

context(option: PresentationOption)
internal fun bgColor() = if (option.darkMode) Color.Black else Color.White

context(option: PresentationOption)
internal fun textColor() = if (option.darkMode) Color.White else Color.Black
