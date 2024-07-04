package io.github.t45k.slidekt

import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.presentation
import io.github.t45k.slidekt.engine.handlePresentation
import kotlin.test.Test

class MainKtTest {

    @Test
    fun testPresentation() {
        val presentation = presentation(PresentationOption(darkMode = true)) {
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

        handlePresentation(presentation)
    }
}
