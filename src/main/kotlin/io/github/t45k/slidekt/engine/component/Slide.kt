package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.util.bgColor

context(PresentationOption)
@Composable
fun Slide(
    matchHeightConstraintsFirst: Boolean,
    content: @Composable () -> Unit,
) = SlideBackground {
    SlideMergin(matchHeightConstraintsFirst) {
        SlideContent { content() }
    }
}

context(PresentationOption)
@Composable
private fun SlideBackground(slideContent: @Composable BoxScope.() -> Unit) =
    Box(Modifier.fillMaxSize().background(Color.Gray), content = slideContent)

context(PresentationOption)
@Composable
private fun SlideMergin(
    matchHeightConstraintsFirst: Boolean,
    content: @Composable ColumnScope.() -> Unit
) = Column(
    Modifier.fillMaxSize()
        .aspectRatio(
            aspectRatio.ratio,
            matchHeightConstraintsFirst,
        )
        .background(bgColor()),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = content,
)

context(PresentationOption)
@Composable
private fun SlideContent(
    content: @Composable ColumnScope.() -> Unit
) = Column(
    Modifier.fillMaxSize(90 / 100F),
    content = content,
)
