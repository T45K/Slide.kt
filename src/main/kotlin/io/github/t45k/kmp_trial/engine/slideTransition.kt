package io.github.t45k.kmp_trial.engine

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.unit.IntOffset
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

    Animation.FROM_ABOVE_TO_BOTTOM -> SlideTransition(
        slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween()
        ),
        slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween()
        ),
        slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween()
        ),
        slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween()
        ),
    )

    Animation.NO_ANIMATION -> SlideTransition(
        slideIn(initialOffset = { IntOffset(0, 0) }),
        slideOut(targetOffset = { IntOffset(0, 0) }),
        slideIn(initialOffset = { IntOffset(0, 0) }),
        slideOut(targetOffset = { IntOffset(0, 0) }),
    )
}
