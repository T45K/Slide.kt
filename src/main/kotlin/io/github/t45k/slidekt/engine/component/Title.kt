package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import io.github.t45k.slidekt.api.Horizontal
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.Title
import io.github.t45k.slidekt.util.sp
import io.github.t45k.slidekt.util.textColor

context(PresentationOption)
@Composable
fun Title(title: Title, slideHeight: Dp) = Box(
    Modifier.fillMaxWidth(),
    contentAlignment = when (title.position) {
        Horizontal.LEFT -> Alignment.CenterStart
        Horizontal.CENTER -> Alignment.Center
    }
) {
    Text(
        text = title.text,
        fontSize = (slideHeight / 12).sp(),
        fontWeight = FontWeight.Bold,
        color = textColor(),
    )
}
