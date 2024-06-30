package io.github.t45k.kmp_trial.engine

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.navigation.NavHostController
import io.github.t45k.kmp_trial.util.onKeyDown

class MoveSlideEventNavController(
    private val navHostController: NavHostController,
    private val slideCount: Int,
) {
    fun moveSlideEvent(): (KeyEvent) -> Boolean = onKeyDown {
        when (it.key) {
            Key.Enter, Key.DirectionRight, Key.DirectionDown -> {
                navigateNext()
                true
            }

            Key.DirectionLeft, Key.DirectionUp -> {
                navigatePrevious()
                true
            }

            else -> false
        }
    }

    private fun navigateNext() {
        val currentIndex = currentIndex()
        if (currentIndex < slideCount) {
            navHostController.navigate((currentIndex + 1).toString())
        }
    }

    private fun navigatePrevious() {
        if (currentIndex() > 1) {
            navHostController.popBackStack()
        }
    }

    private fun currentIndex(): Int = navHostController.currentDestination!!.route!!.toInt()
}
