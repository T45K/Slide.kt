package io.github.t45k.slidekt.api

data class PresentationOption(
    val animation: Animation = Animation.FROM_RIGHT_TO_LEFT,
    val aspectRatio: AspectRatio = AspectRatio.RATIO_16_9,
    val darkMode: Boolean = false,
)

enum class Animation {
    FROM_RIGHT_TO_LEFT,
    FROM_ABOVE_TO_BOTTOM,
    NO_ANIMATION,
}
