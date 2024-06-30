package io.github.t45k.kmp_trial

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t45k.kmp_trial.util.onKeyDown

@Composable
fun Slide(index: Int) {
    Text("Slide $index\nNext ${index + 1}")
}

fun moveSlideEvent(navController: NavHostController): (KeyEvent) -> Boolean = onKeyDown {
    val currentIndex = navController.currentDestination!!.route!!.toInt()
    when (it.key) {
        Key.Enter, Key.DirectionRight -> {
            if (currentIndex < 100) {
                navController.navigate((currentIndex + 1).toString())
            }
            true
        }

        Key.DirectionLeft -> {
            if (currentIndex > 1) {
                navController.navigate((currentIndex - 1).toString())
            }
            true
        }

        else -> false
    }
}

fun main() = application {
    val navController = rememberNavController()
    Window(
        onCloseRequest = ::exitApplication,
        title = "My first app",
        onKeyEvent = moveSlideEvent(navController),
    ) {
        NavHost(navController, startDestination = "1") {
            (1..100).forEach { index ->
                composable(index.toString()) {
                    Slide(index)
                }
            }
        }
    }
}
