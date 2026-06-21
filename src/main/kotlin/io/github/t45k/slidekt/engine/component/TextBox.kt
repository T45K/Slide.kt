package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.Dp
import io.github.t45k.slidekt.api.Horizontal
import io.github.t45k.slidekt.api.Paragraph
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.TextBoxSpec
import io.github.t45k.slidekt.api.Vertical
import io.github.t45k.slidekt.util.sp
import io.github.t45k.slidekt.util.textColor

@Composable
context(option: PresentationOption)
fun TextBox(textBox: TextBoxSpec, slideHeight: Dp) = Column(
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
    with(slideHeight) {
        val textAlign = when (textBox.horizontalPosition) {
            Horizontal.LEFT -> TextAlign.Start
            Horizontal.CENTER -> TextAlign.Center
        }
        with(textAlign) {
            handleParagraphs(textBox.paragraphs)
        }
    }
}

@Composable
context(option: PresentationOption, slideHeight: Dp, textAlign: TextAlign)
private fun ColumnScope.handleParagraphs(paragraphs: List<Paragraph>, indent: Int = 0) {
    paragraphs.forEach { paragraph ->
        ParagraphText(paragraph.text, indent)
        Box(Modifier.fillMaxWidth().padding(slideHeight / 96))

        handleParagraphs(paragraph.nestedParagraphs, indent + 1)

        if (indent == 0) {
            Box(Modifier.fillMaxWidth().padding(slideHeight / 48))
        }
    }
}

@Composable
context(option: PresentationOption, slideHeight: Dp, textAlign: TextAlign)
fun ParagraphText(text: String, indent: Int) = Text(
    text = DecoratedText(text),
    fontSize = (slideHeight / 12 - slideHeight / 96 * indent).sp(),
    color = textColor(),
    modifier = Modifier.fillMaxWidth(),
    textAlign = textAlign,
    style = TextStyle.Default.copy(
        textIndent = TextIndent((slideHeight / 12 * indent).sp()),
    ),
)
