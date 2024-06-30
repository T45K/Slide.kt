package io.github.t45k.kmp_trial

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay

@Composable
@Preview
fun App() {
    MaterialTheme {
        Clock(
            modifier = Modifier
                .aspectRatio(1.0f)
                .padding(100.dp)
        )
    }
}

@Composable
fun Clock(modifier: Modifier) {
    val isStart = remember { mutableStateOf(false) }
    val secondProgress = remember { mutableStateOf(0f) }
    val minProgress = remember { mutableStateOf(0f) }

    LaunchedEffect(isStart.value) {
        while (isStart.value) {
            if (secondProgress.value == 360f) {
                secondProgress.value = 0f
                minProgress.value += 6f
                minProgress.value %= 360f
            }
            secondProgress.value += 6f
            delay(1000)
        }
    }

    Button(
        onClick = { isStart.value = !isStart.value },
        content = {
            if (isStart.value) {
                Text("pause")
            } else {
                Text("Start")
            }
        }
    )

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = Color.Black,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension / 2,
            style = Stroke(5F)
        )
        withTransform(
            { rotate(secondProgress.value) },
            {
                drawLine(
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round,
                    color = Color.Blue,
                    start = center,
                    end = Offset(size.minDimension / 2, 12.dp.toPx())
                )
            }
        )
        withTransform(
            { rotate(minProgress.value) },
            {
                drawLine(
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round,
                    color = Color.Blue,
                    start = center,
                    end = Offset(size.minDimension / 2, 100.dp.toPx())
                )
            }
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "My first app") {
        App()
    }
}
