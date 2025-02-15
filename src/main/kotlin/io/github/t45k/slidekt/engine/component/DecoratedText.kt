package io.github.t45k.slidekt.engine.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import io.github.t45k.slidekt.api.Color
import io.github.t45k.slidekt.engine.component.DecoratedText.BoldText
import io.github.t45k.slidekt.engine.component.DecoratedText.ColorText
import io.github.t45k.slidekt.engine.component.DecoratedText.CompoundText
import io.github.t45k.slidekt.engine.component.DecoratedText.SimpleText
import io.github.t45k.slidekt.engine.component.DecorationTag.BoldBeginTag
import io.github.t45k.slidekt.engine.component.DecorationTag.BoldEndTag
import io.github.t45k.slidekt.engine.component.DecorationTag.ColorBeginTag
import io.github.t45k.slidekt.engine.component.DecorationTag.ColorEndTag
import io.github.t45k.slidekt.util.indicesOf
import androidx.compose.ui.graphics.Color as ComposeColor

fun DecoratedText(text: String): AnnotatedString = buildAnnotatedString {
    val decoratedText = parseDecoration(text)
    appendDecoratedTexts(decoratedText)
}

sealed interface DecoratedText {
    data class SimpleText(val text: String) : DecoratedText
    data class BoldText(val text: DecoratedText) : DecoratedText
    data class ColorText(val text: DecoratedText, val color: ComposeColor) : DecoratedText
    data class CompoundText(val texts: List<DecoratedText>) : DecoratedText {
        constructor(vararg texts: DecoratedText) : this(texts.toList())
    }
}

sealed interface DecorationTag {
    val text: String
    val pair: DecorationTag

    data object BoldBeginTag : DecorationTag {
        override val text: String = "<bold>"
        override val pair: DecorationTag = BoldEndTag
    }

    data object BoldEndTag : DecorationTag {
        override val text: String = "</bold>"
        override val pair: DecorationTag = BoldBeginTag
    }

    data class ColorBeginTag(val color: Color) : DecorationTag {
        override val text: String = "<color $color>"
        override val pair: DecorationTag by lazy { ColorEndTag(color) } // to avoid infinite loop1

        companion object {
            private val REGEX = "<color (.{6})>".toRegex()

            fun findAllFrom(text: String): List<ColorBeginTag> =
                REGEX.findAll(text)
                    .map { ColorBeginTag(Color.fromRgbHex(it.groupValues[1])) }
                    .distinct()
                    .toList()
        }
    }

    data class ColorEndTag(val color: Color) : DecorationTag {
        override val text: String = "</color $color>"
        override val pair: DecorationTag by lazy { ColorBeginTag(color) }
    }
}

context(AnnotatedString.Builder)
fun appendDecoratedTexts(text: DecoratedText) {
    when (text) {
        is SimpleText -> append(text.text)
        is BoldText -> withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { appendDecoratedTexts(text.text) }
        is ColorText -> withStyle(SpanStyle(color = text.color)) { appendDecoratedTexts(text.text) }
        is CompoundText -> text.texts.forEach { appendDecoratedTexts(it) }
    }
}

fun parseDecoration(text: String): DecoratedText {
    require(text.isNotEmpty())

    val decorationTagAndIndex = findFirstBeginTagAndIndex(text)

    val tagIsNotFound = decorationTagAndIndex == null
    if (tagIsNotFound) {
        return SimpleText(text)
    }

    val (decorationTag, index) = decorationTagAndIndex

    val textStartsWithTag = index == 0
    if (!textStartsWithTag) {
        return CompoundText(SimpleText(text.substring(0, index)), parseDecoration(text.substring(index)))
    }

    val endTagIndex = findMatchingEndTagIndex(text, decorationTag, decorationTag.pair)
    val decoratedText = when (decorationTag) {
        is BoldBeginTag -> BoldText(parseDecoration(text.substring(decorationTag.text.length, endTagIndex)))
        is ColorBeginTag -> ColorText(
            parseDecoration(text.substring(decorationTag.text.length, endTagIndex)),
            decorationTag.color.toComposeColor(),
        )

        is BoldEndTag, is ColorEndTag -> throw IllegalArgumentException("Begin tag should be specified here")
    }

    val restText = text.substring(endTagIndex + decorationTag.pair.text.length)
    return if (restText.isEmpty()) decoratedText else CompoundText(decoratedText, parseDecoration(restText))
}

internal fun findFirstBeginTagAndIndex(text: String): Pair<DecorationTag, Int>? {
    val allColorBeginTags = ColorBeginTag.findAllFrom(text)

    return (allColorBeginTags + BoldBeginTag)
        .map { it to text.indexOf(it.text) }
        .filter { (_, index) -> index != -1 }
        .minByOrNull { (_, index) -> index }
}

internal fun findMatchingEndTagIndex(text: String, beginTag: DecorationTag, endTag: DecorationTag): Int {
    require(text.startsWith(beginTag.text))
    require(text.contains(endTag.text))

    val endTagIndices = text.indicesOf(endTag.text)
    val endTagAppearsOnlyJustOnce = endTagIndices.size == 1
    return if (endTagAppearsOnlyJustOnce) endTagIndices[0]
    else {
        val beginTagIndices = text.indicesOf(beginTag.text)
        endTagIndices.mapIndexedNotNull { index, endTagIndex ->
            val beginTagInFrontOfEndTagCounts = beginTagIndices.count { it < endTagIndex }
            if (beginTagInFrontOfEndTagCounts == index + 1) endTagIndex else null
        }.first()
    }
}

private fun Color.toComposeColor(): ComposeColor = ComposeColor(red, green, blue)
