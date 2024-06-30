package io.github.t45k.kmp_trial.api

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
import io.github.t45k.kmp_trial.moveSlideEvent

fun presentation(block: Presentation.() -> Unit) {
    val presentation = Presentation().apply(block)

    application {
        val navController = rememberNavController()
        Window(
            onCloseRequest = ::exitApplication,
            title = "Slide.kt",
            onKeyEvent = moveSlideEvent(navController),
        ) {
            NavHost(navController, startDestination = "1") {
                presentation.slides.forEachIndexed { index, slide ->
                    composable(route = (index + 1).toString()) {
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
}

class Presentation {
    internal val slides: MutableList<Slide> = mutableListOf()

    fun slide(block: Slide.() -> Unit) {
        slides.add(Slide().apply(block))
    }
}

class Slide {
    lateinit internal var title: String
    internal val paragraphs: MutableList<String> = mutableListOf()

    fun title(text: String) {
        title = text
    }

    fun paragraph(text: String) {
        paragraphs += text
    }

    fun par(text: String) = paragraph(text)
}
