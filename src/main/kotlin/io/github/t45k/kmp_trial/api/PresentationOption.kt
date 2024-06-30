package io.github.t45k.kmp_trial.api

data class PresentationOption(
    val animation: Animation = Animation.FROM_RIGHT_TO_LEFT
)

enum class Animation {
    FROM_RIGHT_TO_LEFT,
    FROM_ABOVE_TO_BOTTOM,
    NO_ANIMATION,
}
