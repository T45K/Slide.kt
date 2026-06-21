package io.github.t45k.slidekt.engine

import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import io.github.t45k.slidekt.api.PresentationDsl
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.engine.component.Cover
import io.github.t45k.slidekt.engine.component.Slide
import io.github.t45k.slidekt.engine.component.SourceCode
import io.github.t45k.slidekt.engine.component.TextBox
import io.github.t45k.slidekt.engine.component.Title
import io.github.t45k.slidekt.engine.component.TitleTextBoxSeparator

fun presentation(
    option: () -> PresentationOption = { PresentationOption() },
    dsl: PresentationDsl.() -> Unit,
) = application {
    PresentationApp(PresentationDsl(option()).apply(dsl))
}

fun handlePresentation(presentation: PresentationDsl) = application {
    PresentationApp(presentation)
}

@Composable
private fun ApplicationScope.PresentationApp(presentation: PresentationDsl) {
    val slideTransition = slideTransition(presentation.option.animation)
    val windowState = rememberWindowState()
    val backStack = remember { mutableStateListOf(if (presentation.cover != null) 0 else 1) }

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
                { backStack.last() },
                { backStack.add(it) },
                { backStack.removeLastOrNull() },
                presentation.slides.size,
                hasCover = presentation.cover != null,
            ),
            exportPdfEvent { presentation.exportToPdf() },
        ),
    ) {
        val (_, currentSlideHeight) = calcSlideSize(
            windowState.size,
            presentation.option.aspectRatio
        )
        val matchHeightConstraintsFirst =
            windowState.size.width / windowState.size.height > presentation.option.aspectRatio.ratio

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            transitionSpec = { slideTransition.enter togetherWith slideTransition.exist },
            popTransitionSpec = { slideTransition.popEnter togetherWith slideTransition.popExit },
        ) { index ->
            NavEntry(index) {
                SlideContent(presentation, index, currentSlideHeight, matchHeightConstraintsFirst)
            }
        }
    }
}

/**
 * Renders a single page (the cover when [index] is `0`, otherwise the slide at `index - 1`) of the
 * given [presentation].
 *
 * This composable is shared between the on-screen presentation and the PDF export so that both
 * produce exactly the same layout.
 */
@Composable
internal fun SlideContent(
    presentation: PresentationDsl,
    index: Int,
    slideHeight: Dp,
    matchHeightConstraintsFirst: Boolean,
) {
    with(presentation.option) {
        Slide(matchHeightConstraintsFirst) {
            if (index == 0) {
                presentation.cover?.let { cover -> Cover(cover, slideHeight) }
            } else {
                val slide = presentation.slides[index - 1]

                slide.title?.let { title -> Title(title, slideHeight) }

                if (slide.title != null && (slide.textBox != null || slide.imagePath != null || slide.code != null)) {
                    TitleTextBoxSeparator(slideHeight)
                }

                slide.textBox?.let { textBox -> TextBox(textBox, slideHeight) }

                slide.imagePath?.let {
                    Image(
                        painterResource(it.toString()),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                with(slideHeight) {
                    slide.code?.let {
                        SourceCode(it)
                    }
                }
            }
        }
    }
}
