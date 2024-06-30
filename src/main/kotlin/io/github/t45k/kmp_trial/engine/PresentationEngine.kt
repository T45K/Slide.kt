package io.github.t45k.kmp_trial.engine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t45k.kmp_trial.api.Presentation
import io.github.t45k.kmp_trial.api.PresentationOption

fun handlePresentation(presentation: Presentation, option: PresentationOption) = application {
    val navController = rememberNavController()
    val slideTransition = slideTransition(option.animation)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Slide.kt",
        onKeyEvent = MoveSlideEventNavController(navController, presentation.slides.size).moveSlideEvent(),
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
                    Column(Modifier.fillMaxSize()) {
                        Box(
                            Modifier.fillMaxWidth().padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = slide.title,
                                fontSize = 4.em,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top,
                        ) {
                            slide.paragraphs.forEach { parText ->
                                Text(
                                    text = parText,
                                    fontSize = 3.em,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
