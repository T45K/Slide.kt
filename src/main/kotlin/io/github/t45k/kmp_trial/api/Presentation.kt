package io.github.t45k.kmp_trial.api

import io.github.t45k.kmp_trial.engine.handlePresentation

fun presentation(option: PresentationOption = PresentationOption(), block: Presentation.() -> Unit) {
    val presentation = Presentation().apply(block)

    handlePresentation(presentation, option)
}

class Presentation {
    internal val slides: MutableList<Slide> = mutableListOf()

    fun slide(block: Slide.() -> Unit) {
        slides.add(Slide().apply(block))
    }
}

class Slide {
    internal var title: Title? = null
    internal var textBox: TextBox? = null

    fun title(text: String, position: Horizontal = Horizontal.CENTER) {
        title = Title(text, position)
    }

    fun textBox(
        horizontalPosition: Horizontal = Horizontal.CENTER,
        verticalPosition: Vertical = Vertical.CENTER,
        block: TextBox.() -> Unit,
    ) {
        textBox = TextBox(horizontalPosition, verticalPosition).apply(block)
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

    fun item(text: String) {
        paragraphs += Paragraph.Item(text)
    }

    fun i(text: String) = item(text)

    fun numbering(text: String) {
        paragraphs += Paragraph.Numbering(text)
    }

    fun num(text: String) = numbering(text)
    fun n(text: String) = numbering(text)

    fun sentence(text: String) {
        paragraphs += Paragraph.Sentence(text)
    }

    fun s(text: String) = sentence(text)
}

sealed interface Paragraph {
    val text: String

    data class Item(override val text: String) : Paragraph
    data class Numbering(override val text: String) : Paragraph
    data class Sentence(override val text: String) : Paragraph
}
