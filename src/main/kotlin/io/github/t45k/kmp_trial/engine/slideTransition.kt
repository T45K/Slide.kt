package io.github.t45k.kmp_trial.engine

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import io.github.t45k.kmp_trial.api.Animation

data class SlideTransition(
    val enter: EnterTransition,
    val exist: ExitTransition,
    val popEnter: EnterTransition,
    val popExit: ExitTransition,
)

fun slideTransition(animation: Animation): SlideTransition = when (animation) {
    Animation.FROM_RIGHT_TO_LEFT -> SlideTransition(
        slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween()
        ),
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween()
        ),
        slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween()
        ),
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween()
        ),
    )

    Animation.FROM_ABOVE_TO_BOTTOM -> TODO()
    Animation.NO_ANIMATION -> TODO()
}
