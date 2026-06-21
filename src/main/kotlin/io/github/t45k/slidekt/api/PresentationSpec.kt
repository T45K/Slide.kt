package io.github.t45k.slidekt.api

import java.nio.file.Path
import java.time.LocalDate

@DslMarker
annotation class SlideDsl

data class PresentationSpec(
    val option: PresentationOption,
    val cover: CoverSpec?,
    val slides: List<SlideSpec>,
) {
    val hasCaption: Boolean get() = slides.any { it.caption != null }

    @SlideDsl
    class Builder(private val option: PresentationOption) {
        private var cover: CoverSpec? = null

        private val slides: MutableList<SlideSpec> = mutableListOf()

        fun cover(
            title: String,
            name: String,
            horizontalPosition: Horizontal = Horizontal.CENTER,
            block: CoverSpec.Builder.() -> Unit = {},
        ) {
            cover = CoverSpec.Builder(title, name, horizontalPosition).apply(block).build()
        }

        fun slides(block: SlidesBuilder.() -> Unit) {
            slides.addAll(SlidesBuilder().apply(block).build())
        }

        fun slide(block: SlideSpec.Builder.() -> Unit) {
            slides += SlideSpec.Builder().apply(block).build()
        }

        fun build(): PresentationSpec {
            return PresentationSpec(
                option = option,
                cover = cover,
                slides = slides.toList(),
            )
        }
    }
}

data class CoverSpec(
    val title: String,
    val presenterName: String,
    val position: Horizontal,
    val date: LocalDate? = null,
    val event: String? = null,
    val location: String? = null,
) {

    @SlideDsl
    class Builder(
        private val title: String,
        private val name: String,
        private val position: Horizontal,
    ) {
        private var date: LocalDate? = null
        private var event: String? = null
        private var location: String? = null

        fun date(value: LocalDate) {
            date = value
        }

        fun event(value: String) {
            event = value
        }

        fun location(value: String) {
            location = value
        }

        fun build(): CoverSpec = CoverSpec(
            title = title,
            presenterName = name,
            position = position,
            date = date,
            event = event,
            location = location,
        )
    }
}

@SlideDsl
class SlidesBuilder {
    private val slides: MutableList<SlideSpec> = mutableListOf()

    fun slide(block: SlideSpec.Builder.() -> Unit) {
        slides += SlideSpec.Builder().apply(block).build()
    }

    fun build(): List<SlideSpec> {
        return slides.toList()
    }
}

data class SlideSpec(
    val title: TitleSpec? = null,
    val textBox: TextBoxSpec? = null,
    val imagePath: Path? = null,
    val code: String? = null,
    val caption: String? = null,
) {

    @SlideDsl
    class Builder {
        private var title: TitleSpec? = null
        private var textBox: TextBoxSpec? = null
        private var imagePath: Path? = null
        private var code: String? = null
        private var caption: String? = null

        fun title(text: String, position: Horizontal = Horizontal.CENTER) {
            title = TitleSpec(text, position)
        }

        fun textBox(
            horizontalPosition: Horizontal = Horizontal.CENTER,
            verticalPosition: Vertical = Vertical.CENTER,
            block: TextBoxSpec.Builder.() -> Unit,
        ) {
            textBox = TextBoxSpec.Builder(horizontalPosition, verticalPosition).apply(block).build()
        }

        fun image(path: Path) {
            imagePath = path
        }

        fun code(value: String) {
            code = value
        }

        fun caption(text: String) {
            caption = text
        }

        fun build(): SlideSpec = SlideSpec(
            title = title,
            textBox = textBox,
            imagePath = imagePath,
            code = code,
            caption = caption,
        )
    }
}

data class TitleSpec(
    val text: String,
    val position: Horizontal = Horizontal.CENTER,
)

data class TextBoxSpec(
    val horizontalPosition: Horizontal = Horizontal.CENTER,
    val verticalPosition: Vertical = Vertical.CENTER,
    val paragraphs: List<Paragraph> = emptyList(),
) {
    @SlideDsl
    class Builder(
        private val horizontalPosition: Horizontal,
        private val verticalPosition: Vertical,
    ) {
        private val paragraphs: MutableList<Paragraph> = mutableListOf()

        fun i(text: String, block: Builder.() -> Unit = {}) = item(text, block)
        fun item(text: String, block: Builder.() -> Unit = {}) {
            paragraphs += Paragraph.Item(text, nested(block))
        }

        fun s(text: String, block: Builder.() -> Unit = {}) = sentence(text, block)
        fun sentence(text: String, block: Builder.() -> Unit = {}) {
            paragraphs += Paragraph.Sentence(text, nested(block))
        }

        fun n(text: String, block: Builder.() -> Unit = {}) = numbering(text, block)
        fun numbering(text: String, block: Builder.() -> Unit = {}) {
            paragraphs += Paragraph.Numbering(text, nested(block))
        }

        fun bold(text: String): String = "<bold>$text</bold>"

        fun color(text: String, color: Color): String = "<color $color>$text</color $color>"

        private fun nested(block: Builder.() -> Unit): List<Paragraph> =
            Builder(horizontalPosition, verticalPosition).apply(block).build().paragraphs

        fun build(): TextBoxSpec = TextBoxSpec(
            horizontalPosition = horizontalPosition,
            verticalPosition = verticalPosition,
            paragraphs = paragraphs.toList(),
        )
    }
}

sealed interface Paragraph {
    val text: String
    val nestedParagraphs: List<Paragraph>

    data class Item(
        override val text: String,
        override val nestedParagraphs: List<Paragraph> = emptyList(),
    ) : Paragraph

    data class Numbering(
        override val text: String,
        override val nestedParagraphs: List<Paragraph> = emptyList(),
    ) : Paragraph

    data class Sentence(
        override val text: String,
        override val nestedParagraphs: List<Paragraph> = emptyList(),
    ) : Paragraph
}
