package io.github.t45k.kmp_trial

import io.github.t45k.kmp_trial.api.Horizontal
import io.github.t45k.kmp_trial.api.PresentationOption
import io.github.t45k.kmp_trial.api.Vertical
import io.github.t45k.kmp_trial.api.presentation
import kotlin.test.Test

class MainKtTest {

    @Test
    fun testPresentation() {
        presentation(PresentationOption()) {
            headerSlide(
                "Hello World",
                headerPosition = Horizontal.LEFT,
                paragraphPosition = Horizontal.LEFT to Vertical.TOP
            ) {
                par("Let's start")
            }

            headerSlide("hoge") {
                par("abcd")
                par("efgh")
            }

            headerSlide("fuga") {
                par("1234")
                par("5678")
            }
        }
    }
}
