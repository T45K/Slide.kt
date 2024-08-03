package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.util.sp

context(PresentationOption, /* slideHeight: */ Dp)
@Composable
fun Code(value: String) = Text(
    text = value,
    fontFamily = FontFamily.Monospace,
    fontSize = (this@Dp / 24).sp(),
    color = Color.White,
    modifier = Modifier.fillMaxSize().background(Color.DarkGray),
    textAlign = TextAlign.Left,
)
