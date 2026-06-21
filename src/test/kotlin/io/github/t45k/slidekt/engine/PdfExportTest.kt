package io.github.t45k.slidekt.engine

import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.PresentationSpec
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import java.time.LocalDate
import kotlin.io.path.createTempFile
import kotlin.io.path.deleteIfExists
import kotlin.io.path.fileSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PdfExportTest {
    @Test
    fun exportsCoverAndEverySlideAsItsOwnPage() {
        val presentation = PresentationSpec.Builder(PresentationOption()).apply {
            cover("Slide.kt", "Tasuku Nakagawa") {
                date(LocalDate.of(2024, 1, 1))
            }
            slides {
                slide {
                    title("Hello World")
                    textBox { s("Let's start") }
                }
                slide {
                    title("Syntax Highlighting")
                    code("fun main() = println(\"Hello\")")
                }
            }
        }.build()

        val output = createTempFile(prefix = "slide-kt-test", suffix = ".pdf")
        try {
            presentation.exportToPdf(output)

            assertTrue(output.fileSize() > 0, "The generated PDF should not be empty")
            PDDocument.load(output.toFile()).use { document ->
                // 1 cover + 2 slides
                assertEquals(3, document.numberOfPages)
                val mediaBox = document.getPage(0).mediaBox
                assertTrue(
                    mediaBox.width > mediaBox.height,
                    "A 16:9 slide must be exported in landscape orientation",
                )
            }
        } finally {
            output.deleteIfExists()
        }
    }

    @Test
    fun rendersNonBlankPages() {
        val presentation = PresentationSpec.Builder(PresentationOption()).apply {
            slide {
                title("Visible content")
                textBox { s("This text must actually be drawn on the page") }
            }
        }.build()

        val output = createTempFile(prefix = "slide-kt-test", suffix = ".pdf")
        try {
            presentation.exportToPdf(output)

            PDDocument.load(output.toFile()).use { document ->
                val rendered = PDFRenderer(document).renderImage(0)
                val distinctColors = buildSet {
                    for (x in 0 until rendered.width step 4) {
                        for (y in 0 until rendered.height step 4) {
                            add(rendered.getRGB(x, y))
                        }
                    }
                }
                assertTrue(distinctColors.size > 1, "A rendered slide must not be a blank page")
            }
        } finally {
            output.deleteIfExists()
        }
    }

    @Test
    fun exportsPresentationWithoutCover() {
        val presentation = PresentationSpec.Builder(PresentationOption()).apply {
            slide {
                title("Only slide")
                textBox { s("No cover here") }
            }
        }.build()

        val output = createTempFile(prefix = "slide-kt-test", suffix = ".pdf")
        try {
            presentation.exportToPdf(output)

            PDDocument.load(output.toFile()).use { document ->
                assertEquals(1, document.numberOfPages)
            }
        } finally {
            output.deleteIfExists()
        }
    }
}
