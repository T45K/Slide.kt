package io.github.t45k.slidekt.engine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t45k.slidekt.api.Horizontal
import io.github.t45k.slidekt.api.Presentation
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.Vertical

fun handlePresentation(presentation: Presentation, option: PresentationOption) = application {
    val navController = rememberNavController()
    val slideTransition = slideTransition(option.animation)
    val windowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Slide.kt",
        onKeyEvent = moveSlideEvent(
            { navController.currentDestination!!.route!!.toInt() },
            { navController.navigate(it.toString()) },
            { navController.popBackStack() },
            presentation.slides.size,
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
                    Box(Modifier.fillMaxSize().background(Color.Black)) {
                        Column(
                            Modifier.fillMaxSize()
                                .aspectRatio(
                                    option.aspectRatio.ratio,
                                    (windowState.size.width) / (windowState.size.height) > option.aspectRatio.ratio
                                )
                                .background(Color.White)
                                .padding(
                                    horizontal = windowState.size.height / 72,
                                    vertical = windowState.size.height / 72,
                                )
                        ) {
                            slide.title?.let { title ->
                                Box(
                                    Modifier.fillMaxWidth(),
                                    contentAlignment = when (title.position) {
                                        Horizontal.LEFT -> Alignment.CenterStart
                                        Horizontal.CENTER -> Alignment.Center
                                    }
                                ) {
                                    Text(
                                        text = title.text,
                                        fontSize = (windowState.size.height / 12).value.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }

                            Box(Modifier.fillMaxWidth().padding(vertical = 4.dp))

                            slide.textBox?.let { textBox ->
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = when (textBox.verticalPosition) {
                                        Vertical.TOP -> Arrangement.Top
                                        Vertical.CENTER -> Arrangement.Center
                                    },
                                    horizontalAlignment = when (textBox.horizontalPosition) {
                                        Horizontal.LEFT -> Alignment.Start
                                        Horizontal.CENTER -> Alignment.CenterHorizontally
                                    },
                                ) {
                                    textBox.paragraphs.forEach { paragraph ->
                                        Text(
                                            text = paragraph.text,
                                            fontSize = (windowState.size.height / 16).value.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
