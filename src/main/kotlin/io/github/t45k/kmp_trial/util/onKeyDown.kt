package io.github.t45k.kmp_trial.util

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type

fun onKeyDown(block: (KeyEvent) -> Boolean): (KeyEvent) -> Boolean = {
    if (it.type == KeyEventType.KeyDown) block(it)
    else false
}
