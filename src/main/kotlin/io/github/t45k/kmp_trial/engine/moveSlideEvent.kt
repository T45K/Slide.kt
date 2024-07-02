package io.github.t45k.kmp_trial.engine

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import io.github.t45k.kmp_trial.util.onKeyDown

fun moveSlideEvent(
    currentIndex: () -> Int,
    navigate: (Int) -> Unit,
    navigateBack: () -> Unit,
    slideCount: Int,
): (KeyEvent) -> Boolean = onKeyDown {
    when (it.key) {
        Key.Enter, Key.DirectionRight, Key.DirectionDown -> {
            val index = currentIndex()
            if (index < slideCount) {
                navigate(index + 1)
            }
            true
        }

        Key.DirectionLeft, Key.DirectionUp -> {
            if (currentIndex() > 1) {
                navigateBack()
            }
            true
        }

        else -> false
    }
}
