package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import io.github.t45k.slidekt.api.Cover
import io.github.t45k.slidekt.api.Horizontal
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.util.sp
import io.github.t45k.slidekt.util.textColor
import java.time.format.DateTimeFormatter

context(PresentationOption)
@Composable
fun Cover(cover: Cover, slideHeight: Dp) {
    Box(
        Modifier.fillMaxWidth().fillMaxHeight(2 / 3f),
        contentAlignment = when (cover.position) {
            Horizontal.LEFT -> Alignment.CenterStart
            Horizontal.CENTER -> Alignment.Center
        },
    ) {
        Text(
            text = cover.title,
            fontSize = (slideHeight / 6).sp(),
            fontWeight = FontWeight.Bold,
            color = textColor(),
        )
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = when (cover.position) {
            Horizontal.LEFT -> Alignment.CenterStart
            Horizontal.CENTER -> Alignment.Center
        },
    ) {
        Column {
            cover.event?.let { event ->
                Text(
                    text = event,
                    fontSize = (slideHeight / 12).sp(),
                    color = textColor(),
                )
            }

            cover.date?.let { date ->
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd（E）")),
                    fontSize = (slideHeight / 12).sp(),
                    color = textColor(),
                )
            }

            Text(
                text = cover.presenterName,
                fontSize = (slideHeight / 12).sp(),
                fontWeight = FontWeight.Bold,
                color = textColor(),
            )
        }
    }
}
