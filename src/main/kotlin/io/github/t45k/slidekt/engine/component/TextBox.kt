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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import io.github.t45k.slidekt.api.Horizontal
import io.github.t45k.slidekt.api.Paragraph
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.TextBox
import io.github.t45k.slidekt.api.Vertical
import io.github.t45k.slidekt.util.sp
import io.github.t45k.slidekt.util.textColor

context(PresentationOption)
@Composable
fun TextBox(textBox: TextBox, slideHeight: Dp) = Column(
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

context(PresentationOption, /* slideHeight: */ Dp, TextAlign)
@Composable
private fun ColumnScope.handleParagraphs(paragraphs: List<Paragraph>, indent: Int = 0) {
    paragraphs.forEach { paragraph ->
        ParagraphText(paragraph.text, indent)
        Box(Modifier.fillMaxWidth().padding(this@Dp / 96))

        handleParagraphs(paragraph.nestedParagraphs, indent + 1)

        if (indent == 0) {
            Box(Modifier.fillMaxWidth().padding(this@Dp / 48))
        }
    }
}

context(PresentationOption, /* slideHeight: */ Dp, TextAlign)
@Composable
fun ParagraphText(text: String, indent: Int) = Text(
    text = buildAnnotatedString { deserialize(parseDecoration(text)) },
    fontSize = (this@Dp / 12 - this@Dp / 96 * indent).sp(),
    color = textColor(),
    modifier = Modifier.fillMaxWidth(),
    textAlign = this@TextAlign,
    style = TextStyle.Default.copy(
        textIndent = TextIndent((this@Dp / 12 * indent).sp())
    )
)

context(AnnotatedString.Builder)
fun deserialize(obj: DecoratedText) {
    when (obj) {
        is DecoratedText.SimpleText -> append(obj.text)
        is DecoratedText.BoldText -> withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { deserialize(obj.text) }
        is DecoratedText.CompoundText -> obj.texts.forEach { deserialize(it) }
    }
}

fun parseDecoration(text: String): DecoratedText {
    var startIndex = text.indexOf(DecoratedText.BoldText.BEGIN_TAG)
    return if (startIndex == -1) {
        DecoratedText.SimpleText(text)
    } else {
        val children = mutableListOf<DecoratedText>()
        var tmp = text
        if (startIndex != 0) {
            children += DecoratedText.SimpleText(tmp.substring(0, startIndex))
            tmp = tmp.substring(startIndex, tmp.length)
        }

        val endTagIndex = findMatchingEndTag(tmp)
        children += DecoratedText.BoldText(
            parseDecoration(
                tmp.substring(
                    DecoratedText.BoldText.BEGIN_TAG.length,
                    endTagIndex - DecoratedText.BoldText.END_TAG.length
                )
            )
        )

        tmp = tmp.substring(endTagIndex)
        if (tmp.isNotEmpty()) {
            children += parseDecoration(tmp)
        }
        return DecoratedText.CompoundText(children)
    }
}

typealias EndTagIndex = Int

private fun findMatchingEndTag(beginTagIncludingText: String): EndTagIndex {
    val (beginTag, endTag) = when {
        beginTagIncludingText.startsWith(DecoratedText.BoldText.BEGIN_TAG) -> DecoratedText.BoldText.BEGIN_TAG to DecoratedText.BoldText.END_TAG
        else -> throw IllegalArgumentException("Input should start with begin tag")
    }

    val firstIndexOfEndTag = beginTagIncludingText.indexOf(endTag)
    val lastIndexOfEndTag = beginTagIncludingText.lastIndexOf(endTag)
    val endTagAppearsOnlyJustOnce = firstIndexOfEndTag == lastIndexOfEndTag
    if (endTagAppearsOnlyJustOnce) {
        return firstIndexOfEndTag + endTag.length
    } else {
        var count = 0
        for (index in 1..<beginTagIncludingText.length) {
            when {
                beginTagIncludingText.substring(index).startsWith(beginTag) -> {
                    count++
                }

                beginTagIncludingText.substring(index).startsWith(endTag) -> {
                    if (count == 0) {
                        return index + endTag.length
                    } else {
                        count--
                    }
                }
            }
        }
        throw IllegalArgumentException("Input should contain matched end tag")
    }
}

sealed interface DecoratedText {
    data class SimpleText(val text: String) : DecoratedText
    data class BoldText(val text: DecoratedText) : DecoratedText {
        companion object {
            const val BEGIN_TAG = "<bold>"
            const val END_TAG = "</bold>"
        }
    }

    data class CompoundText(val texts: List<DecoratedText>) : DecoratedText {
        constructor(vararg texts: DecoratedText) : this(texts.toList())
    }
}
