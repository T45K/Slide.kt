package io.github.t45k.slidekt.api

enum class AspectRatio(width: Int, height: Int) {
    RATIO_16_9(16, 9), RATIO_4_3(4, 3);

    val ratio: Float = width.toFloat() / height.toFloat()
}
