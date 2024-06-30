package io.github.t45k.kmp_trial.api

import io.github.t45k.kmp_trial.engine.handlePresentation

fun presentation(option: PresentationOption = PresentationOption(), block: Presentation.() -> Unit) {
    val presentation = Presentation().apply(block)

    handlePresentation(presentation, option)
}

class Presentation {
    internal val slides: MutableList<HeaderSlide> = mutableListOf()

    fun headerSlide(
        title: String,
        headerPosition: Horizontal = Horizontal.CENTER,
        paragraphPosition: Pair<Horizontal, Vertical> = Horizontal.CENTER to Vertical.CENTER,
        block: HeaderSlide.() -> Unit,
    ) {
        slides.add(HeaderSlide(title, headerPosition, paragraphPosition).apply(block))
    }
}

class HeaderSlide(
    internal val title: String,
    internal val headerPosition: Horizontal,
    internal val paragraphPosition: Pair<Horizontal, Vertical>,
) {
    internal val paragraphs: MutableList<String> = mutableListOf()

    fun paragraph(text: String) {
        paragraphs += text
    }

    fun par(text: String) = paragraph(text)
}
