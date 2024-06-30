package io.github.t45k.kmp_trial.engine

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.unit.sp
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
        onKeyEvent = moveSlideEvent(navController),
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
                    Box(
                        Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = slide.title,
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        slide.paragraphs.forEach { parText ->
                            Text(
                                text = parText,
                                fontSize = 32.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}
