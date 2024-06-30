package io.github.t45k.kmp_trial

import io.github.t45k.kmp_trial.api.presentation
import kotlin.test.Test

class MainKtTest {

    @Test
    fun testPresentation() {
        presentation {
            slide {
                title("Hello World")
                par("Let's start")
            }

            slide {
                title("hoge")
                par("abcd")
                par("efgh")
            }

            slide {
                title("fuga")
                par("1234")
                par("5678")
            }
        }
    }
}
