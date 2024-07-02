package io.github.t45k.kmp_trial

import io.github.t45k.kmp_trial.api.PresentationOption
import io.github.t45k.kmp_trial.api.presentation
import kotlin.test.Test

class MainKtTest {

    @Test
    fun testPresentation() {
        presentation(PresentationOption()) {
            slide {
                title("Hello World")
                textBox {
                    s("Let's start")
                }
            }

            slide {
                title("Second")
                textBox {
                    s("abcd")
                    s("efgh")
                }
            }

            slide {
                title("Third")
                textBox {
                    s("1234")
                    s("5678")
                }
            }
        }
    }
}
