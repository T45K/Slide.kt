package io.github.t45k.slidekt.engine

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t45k.slidekt.api.Presentation
import io.github.t45k.slidekt.engine.component.Slide
import io.github.t45k.slidekt.engine.component.TextBox
import io.github.t45k.slidekt.engine.component.Title
import io.github.t45k.slidekt.engine.component.TitleTextBoxSeparator

fun handlePresentation(presentation: Presentation) = application {
    val navController = rememberNavController()
    val slideTransition = slideTransition(presentation.option.animation)
    val windowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Slide.kt",
        onKeyEvent = mergeKeyEvent(
            fullScreenEvent(
                { windowState.placement == WindowPlacement.Fullscreen },
                { windowState.placement = WindowPlacement.Fullscreen },
                { windowState.placement = WindowPlacement.Floating },
            ),
            moveSlideEvent(
                { navController.currentDestination!!.route!!.toInt() },
                { navController.navigate(it.toString()) },
                { navController.popBackStack() },
                presentation.slides.size,
            )
        ),
    ) {
        NavHost(navController, startDestination = "1") {
            presentation.slides.forEachIndexed { index, slide ->
                composable(
                    route = (index + 1).toString(),
                    enterTransition = { slideTransition.enter },
                    exitTransition = { slideTransition.exist },
                    popEnterTransition = { slideTransition.popEnter },
                    popExitTransition = { slideTransition.popExit }
                ) {
                    val (currentSlideWidth, currentSlideHeight) = calcSlideSize(
                        windowState.size,
                        presentation.option.aspectRatio
                    )
                    val matchHeightConstraintsFirst =
                        windowState.size.width / windowState.size.height > presentation.option.aspectRatio.ratio

                    with(presentation.option) {
                        Slide(currentSlideHeight, currentSlideWidth, matchHeightConstraintsFirst) {
                            slide.title?.let { title -> Title(title, currentSlideHeight) }

                            if (slide.title != null && slide.textBox != null) {
                                TitleTextBoxSeparator(currentSlideHeight)
                            }

                            slide.textBox?.let { textBox -> TextBox(textBox, currentSlideHeight) }

                            slide.imagePath?.let {
                                Image(
                                    painterResource(it.toString()),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
