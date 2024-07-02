package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import io.github.t45k.slidekt.api.Horizontal
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
    textBox.paragraphs.forEach { paragraph ->
        Text(
            text = paragraph.text, // TODO: when(paragraph)
            fontSize = (slideHeight / 16).sp(),
            color = textColor(),
        )
    }
}
