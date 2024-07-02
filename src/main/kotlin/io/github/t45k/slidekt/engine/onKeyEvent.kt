package io.github.t45k.slidekt.engine

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

typealias OnKeyEvent = (KeyEvent) -> Boolean

fun mergeKeyEvent(vararg onKeyEvents: OnKeyEvent): OnKeyEvent = { keyEvent ->
    onKeyEvents.any { it(keyEvent) }
}

internal fun onKeyDown(block: (KeyEvent) -> Boolean): OnKeyEvent = {
    if (it.type == KeyEventType.KeyDown) block(it)
    else false
}

internal fun fullScreenEvent(
    isFullScreen: () -> Boolean,
    replaceFullScreen: () -> Unit,
    revertFullScreen: () -> Unit,
): OnKeyEvent = onKeyDown {
    when {
        !isFullScreen() && (it.isCtrlPressed || it.isMetaPressed) && it.key == Key.Enter -> {
            replaceFullScreen()
            true
        }

        isFullScreen() && it.key == Key.Escape -> {
            revertFullScreen()
            true
        }

        !isFullScreen() && it.isCtrlPressed && it.key == Key.F -> {
            replaceFullScreen()
            true
        }

        isFullScreen() && it.isCtrlPressed && it.key == Key.F -> {
            revertFullScreen()
            true
        }

        else -> false
    }
}

internal fun moveSlideEvent(
    currentIndex: () -> Int,
    navigate: (Int) -> Unit,
    navigateBack: () -> Unit,
    slideCount: Int,
): OnKeyEvent = onKeyDown {
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
