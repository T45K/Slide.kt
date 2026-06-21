package io.github.t45k.slidekt.engine

import androidx.compose.ui.renderComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.PresentationSpec
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.jetbrains.skia.EncodedImageFormat
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.outputStream

/** Height (in pixels) of every rendered slide. The width is derived from the aspect ratio. */
private const val SLIDE_HEIGHT_PX: Int = 1080

/**
 * Builds the [PresentationSpec] described by [dsl] and writes it to [output] as a PDF file
 * without opening any window. Convenient for generating slides in a headless environment.
 */
fun presentationPdf(
    output: Path = Path("slide.pdf"),
    option: () -> PresentationOption = { PresentationOption() },
    dsl: PresentationSpec.Builder.() -> Unit,
) {
    PresentationSpec.Builder(option()).apply(dsl).build().exportToPdf(output)
}

/**
 * Renders every page of this presentation (the optional cover followed by all the slides) and
 * writes them to [output] as a single PDF file, one slide per page.
 */
fun PresentationSpec.exportToPdf(output: Path = Path("slide.pdf")) {
    val height = SLIDE_HEIGHT_PX
    val width = (height * option.aspectRatio.ratio).toInt()

    val pageIndices = buildList {
        if (cover != null) add(0)
        slides.indices.forEach { slideIndex -> add(slideIndex + 1) }
    }
    check(pageIndices.isNotEmpty()) { "Presentation has neither a cover nor a slide to export" }

    PDDocument().use { document ->
        pageIndices.forEach { index ->
            val png = renderSlideToPng(index, width, height)
            val page = PDPage(PDRectangle(width.toFloat(), height.toFloat()))
            document.addPage(page)

            val image = PDImageXObject.createFromByteArray(document, png, "slide_$index")
            PDPageContentStream(document, page).use { stream ->
                stream.drawImage(image, 0f, 0f, width.toFloat(), height.toFloat())
            }
        }
        output.outputStream().use { stream -> document.save(stream) }
    }
}

/**
 * Renders a single page of this presentation off-screen into a PNG byte array, mirroring the
 * sizing logic used by the on-screen presentation so that the exported page looks identical.
 */
private fun PresentationSpec.renderSlideToPng(index: Int, width: Int, height: Int): ByteArray {
    val image = renderComposeScene(width = width, height = height, density = Density(1f)) {
        val (_, slideHeight) = calcSlideSize(DpSize(width.dp, height.dp), option.aspectRatio)
        val matchHeightConstraintsFirst = width.toFloat() / height.toFloat() > option.aspectRatio.ratio
        SlideContent(this@renderSlideToPng, index, slideHeight, matchHeightConstraintsFirst)
    }
    return image.encodeToData(EncodedImageFormat.PNG)?.bytes
        ?: error("Failed to encode slide #$index into PNG")
}
