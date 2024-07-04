package io.github.t45k.slidekt

import io.github.t45k.slidekt.api.Horizontal
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.presentation
import io.github.t45k.slidekt.engine.handlePresentation
import kotlin.io.path.Path
import kotlin.test.Test

class MainKtTest {

    @Test
    fun testPresentation() {
        val presentation = presentation(PresentationOption(darkMode = true)) {
            slide {
                title("Hello World")
                textBox {
                    s("Let's start") {
                        s("This is nest") {
                            s("nest one more")
                        }
                    }
                }
            }

            slide {
                title("Second")
                textBox(horizontalPosition = Horizontal.LEFT) {
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

            slide {
                image(Path("me.png"))
            }
        }

        handlePresentation(presentation)
    }
}
