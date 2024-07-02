package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import io.github.t45k.slidekt.api.PresentationOption

@Composable
fun Slide(
    option: PresentationOption,
    slideHeight: Dp,
    matchHeightConstraintsFirst: Boolean,
    content: @Composable () -> Unit,
) = SlideBackground {
    SlideContent(option, slideHeight, matchHeightConstraintsFirst) { content() }
}

@Composable
private fun SlideBackground(slideContent: @Composable BoxScope.() -> Unit) =
    Box(Modifier.fillMaxSize().background(Color.Black), content = slideContent)

@Composable
private fun SlideContent(
    option: PresentationOption,
    slideHeight: Dp,
    matchHeightConstraintsFirst: Boolean,
    content: @Composable ColumnScope.() -> Unit
) = Column(
    Modifier.fillMaxSize()
        .aspectRatio(
            option.aspectRatio.ratio,
            matchHeightConstraintsFirst,
        )
        .background(Color.White)
        .padding(
            horizontal = slideHeight / 72,
            vertical = slideHeight / 72,
        ),
    content = content,
)
