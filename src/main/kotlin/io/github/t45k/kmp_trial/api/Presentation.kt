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
    internal lateinit var title: String
    internal val paragraphs: MutableList<String> = mutableListOf()

    fun title(text: String) {
        title = text
    }

    fun paragraph(text: String) {
        paragraphs += text
    }

    fun par(text: String) = paragraph(text)
}
