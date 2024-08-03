@file:OptIn(ExperimentalStdlibApi::class)

package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.internal.isNewLine
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.util.sp

context(PresentationOption, /* slideHeight: */ Dp)
@Composable
fun Code(value: String) = Box {
    Row(Modifier.fillMaxSize()) {
        Text(
            text = (1..value.count { it.isNewLine() } + 1).joinToString("\n"),
            fontFamily = FontFamily.Monospace,
            fontSize = (this@Dp / 24).sp(),
            color = Color.DarkGray,
            modifier = Modifier.background("1E1F22".toColor()).padding(end = this@Dp / 24), // same as IntelliJ
            textAlign = TextAlign.Right,
        )
        Text(
            text = lexCode(value),
            fontFamily = FontFamily.Monospace,
            fontSize = (this@Dp / 24).sp(),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().background("1E1F22".toColor()), // same as IntelliJ
            textAlign = TextAlign.Left,
        )
    }
}

private val lexer = Highlights.Builder()
    .language(SyntaxLanguage.KOTLIN)
    .theme(SyntaxThemes.default(true))

fun lexCode(code: String): AnnotatedString {
    val highlights = lexer.code(code).build()
        .getHighlights()
        .sortedBy { it.location.start }

    return buildAnnotatedString {
        code.forEachIndexed { index, c ->
            val highlightInfoIndex = highlights.binarySearch {
                when {
                    it.location.start <= index && index < it.location.end -> 0
                    index < it.location.start -> 1
                    else -> -1
                }
            }

            if (highlightInfoIndex >= 0) {
                val highlight = highlights[highlightInfoIndex]
                val style = when (highlight) {
                    is BoldHighlight -> SpanStyle(fontWeight = FontWeight.Bold)
                    is ColorHighlight -> SpanStyle(color = highlight.rgb.toColor())
                }

                withStyle(style = style) {
                    append(c)
                }
            } else {
                append(c)
            }
        }
    }
}

private fun Int.toColor(): Color = toHexString().substring(2).toColor()

private fun String.toColor(): Color = Color(
    red = substring(0..1).hexToInt(),
    green = substring(2..3).hexToInt(),
    blue = substring(4..5).hexToInt(),
)
