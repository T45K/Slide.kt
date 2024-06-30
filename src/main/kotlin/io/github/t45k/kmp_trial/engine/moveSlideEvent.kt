package io.github.t45k.kmp_trial.engine

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.navigation.NavHostController
import io.github.t45k.kmp_trial.util.onKeyDown

fun moveSlideEvent(navController: NavHostController): (KeyEvent) -> Boolean = onKeyDown {
    val currentIndex = navController.currentDestination!!.route!!.toInt()
    when (it.key) {
        Key.Enter, Key.DirectionRight, Key.DirectionDown -> {
            if (currentIndex < 100) {
                navController.navigate((currentIndex + 1).toString())
            }
            true
        }

        Key.DirectionLeft, Key.DirectionUp -> {
            if (currentIndex > 1) {
                navController.popBackStack()
            }
            true
        }

        else -> false
    }
}
