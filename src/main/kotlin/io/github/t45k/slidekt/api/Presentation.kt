package io.github.t45k.slidekt.api

import java.nio.file.Path

fun presentation(
    option: PresentationOption = PresentationOption(),
    block: Presentation.() -> Unit
): Presentation = Presentation(option).apply(block)

class Presentation(internal val option: PresentationOption = PresentationOption()) {
    internal val slides: MutableList<Slide> = mutableListOf()

    fun slide(block: Slide.() -> Unit) {
        slides.add(Slide().apply(block))
    }
}

class Slide {
    internal var title: Title? = null
    internal var textBox: TextBox? = null
    internal var imagePath: Path? = null

    fun title(text: String, position: Horizontal = Horizontal.CENTER) {
        title = Title(text, position)
    }

    fun title(position: Horizontal = Horizontal.CENTER, block: () -> String) = title(block(), position)

    fun textBox(
        horizontalPosition: Horizontal = Horizontal.CENTER,
        verticalPosition: Vertical = Vertical.CENTER,
        block: TextBox.() -> Unit,
    ) {
        textBox = TextBox(horizontalPosition, verticalPosition).apply(block)
    }

    fun image(path: Path) {
        imagePath = path
    }
}

data class Title(
    internal val text: String,
    internal val position: Horizontal = Horizontal.CENTER,
)

class TextBox(
    internal val horizontalPosition: Horizontal = Horizontal.CENTER,
    internal val verticalPosition: Vertical = Vertical.CENTER,
) {
    internal val paragraphs: MutableList<Paragraph> = mutableListOf()

    fun item(text: String, nestedParagraphsBlock: (TextBox.() -> Unit) = { }) {
        val nestedParagraphs = TextBox(horizontalPosition, verticalPosition).apply(nestedParagraphsBlock).paragraphs
        paragraphs += Paragraph.Item(text, nestedParagraphs)
    }

    fun i(text: String, nestedParagraphsBlock: (TextBox.() -> Unit) = { }) = item(text, nestedParagraphsBlock)

    fun numbering(text: String, nestedParagraphsBlock: (TextBox.() -> Unit) = { }) {
        val nestedParagraphs = TextBox(horizontalPosition, verticalPosition).apply(nestedParagraphsBlock).paragraphs
        paragraphs += Paragraph.Numbering(text, nestedParagraphs)
    }

    fun num(text: String, nestedParagraphsBlock: (TextBox.() -> Unit) = { }) = numbering(text, nestedParagraphsBlock)
    fun n(text: String, nestedParagraphsBlock: (TextBox.() -> Unit) = { }) = numbering(text, nestedParagraphsBlock)

    fun sentence(text: String, nestedParagraphsBlock: (TextBox.() -> Unit) = { }) {
        val nestedParagraphs = TextBox(horizontalPosition, verticalPosition).apply(nestedParagraphsBlock).paragraphs
        paragraphs += Paragraph.Sentence(text, nestedParagraphs)
    }

    fun s(text: String, nestedParagraphsBlock: (TextBox.() -> Unit) = { }) = sentence(text, nestedParagraphsBlock)
}

sealed interface Paragraph {
    val text: String
    val nestedParagraphs: List<Paragraph>

    data class Item(
        override val text: String,
        override val nestedParagraphs: List<Paragraph> = emptyList()
    ) : Paragraph

    data class Numbering(
        override val text: String,
        override val nestedParagraphs: List<Paragraph> = emptyList(),
    ) : Paragraph

    data class Sentence(
        override val text: String,
        override val nestedParagraphs: List<Paragraph> = emptyList()
    ) : Paragraph
}
