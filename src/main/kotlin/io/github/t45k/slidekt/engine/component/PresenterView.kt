package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.PresentationSpec
import io.github.t45k.slidekt.engine.SlideContent
import io.github.t45k.slidekt.engine.calcSlideSize
import io.github.t45k.slidekt.util.bgColor
import io.github.t45k.slidekt.util.textColor

/**
 * Presenter-facing view rendered in the secondary window. Shows previews of the current and next
 * slides side by side, the caption (speaker notes) for the current slide, and the page number.
 *
 * [currentIndex] follows the same numbering as the on-screen presentation: `0` is the optional
 * cover and `index - 1` indexes into [PresentationSpec.slides].
 */
@Composable
fun PresenterView(presentation: PresentationSpec, currentIndex: Int) = with(presentation.option) {
    val hasCover = presentation.cover != null
    val totalPages = (if (hasCover) 1 else 0) + presentation.slides.size
    val currentPage = if (hasCover) currentIndex + 1 else currentIndex
    val caption = if (currentIndex == 0) null else presentation.slides[currentIndex - 1].caption

    Column(
        Modifier.fillMaxSize().background(bgColor()).padding(16.dp),
    ) {
        Row(Modifier.fillMaxWidth().weight(0.5f)) {
            SlidePreview(presentation, currentIndex, "現在", Modifier.weight(1f).fillMaxHeight())
            Spacer(Modifier.width(16.dp))
            if (currentIndex < presentation.slides.size) {
                SlidePreview(presentation, currentIndex + 1, "次", Modifier.weight(1f).fillMaxHeight())
            } else {
                NextPlaceholder(Modifier.weight(1f).fillMaxHeight())
            }
        }

        Spacer(Modifier.fillMaxWidth().padding(8.dp))

        Box(
            Modifier.fillMaxWidth().weight(0.5f).verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = caption.orEmpty(),
                fontSize = 28.sp,
                color = textColor(),
            )
        }

        Text(
            text = "$currentPage / $totalPages",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor(),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        )
    }
}

@Composable
context(option: PresentationOption)
private fun SlidePreview(
    presentation: PresentationSpec,
    index: Int,
    label: String,
    modifier: Modifier,
) = Column(modifier) {
    Text(text = label, fontSize = 16.sp, color = textColor(), modifier = Modifier.padding(bottom = 4.dp))
    BoxWithConstraints(Modifier.fillMaxWidth().weight(1f)) {
        val (_, slideHeight) = calcSlideSize(DpSize(maxWidth, maxHeight), option.aspectRatio)
        val matchHeightConstraintsFirst = maxWidth / maxHeight > option.aspectRatio.ratio
        SlideContent(presentation, index, slideHeight, matchHeightConstraintsFirst)
    }
}

@Composable
context(_: PresentationOption)
private fun NextPlaceholder(modifier: Modifier) = Column(modifier) {
    Text(text = "次", fontSize = 16.sp, color = textColor(), modifier = Modifier.padding(bottom = 4.dp))
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "（最終スライド）", fontSize = 20.sp, color = textColor())
    }
}
