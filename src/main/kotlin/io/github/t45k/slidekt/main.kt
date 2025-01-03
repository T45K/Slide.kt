package io.github.t45k.slidekt

import io.github.t45k.slidekt.api.Color
import io.github.t45k.slidekt.api.Horizontal
import io.github.t45k.slidekt.api.PresentationOption
import io.github.t45k.slidekt.api.presentation
import io.github.t45k.slidekt.engine.handlePresentation
import kotlin.io.path.Path
import java.time.LocalDate

fun main() {
    val presentation = presentation(PresentationOption(darkMode = true)) {
        cover("Slide.ktのご紹介", "Tasuku Nakagawa") {
            date(LocalDate.of(2024, 1, 1))
        }

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
                s("こんにちは") {
                    s("こんばんは")
                    s("おはよう")
                }
                s("さようなら") {
                    s("また今度")
                }
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
            title("Me")
            image(Path("me.png"))
        }

        slide {
            title("Decoration")
            textBox {
                s("This is ${bold("bold")}. you know say?")
                s("This is ${color("color", Color.RED)}. beautiful")
                s("${bold(color("Both bold and color", Color.BLUE))} can be applied")
                s(color("${bold("Bold")} and not bold", Color.YELLOW))
            }
        }

        slide {
            title("Syntax Highlighting")
            code(
                """
                        class Hoge {
                          fun hoge() = println("Hello world")
                        }
                    """.trimIndent()
            )
        }
    }

//    handlePresentation(presentation)
    handlePresentation {
        presentation(PresentationOption(darkMode = true)) {
            cover("Slide.ktのご紹介", "Tasuku Nakagawa") {
                date(LocalDate.of(2024, 1, 1))
            }

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
                    s("こんにちは") {
                        s("こんばんは")
                        s("おはよう")
                    }
                    s("さようなら") {
                        s("また今度")
                    }
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
                title("Me")
                image(Path("me.png"))
            }

            slide {
                title("Decoration")
                textBox {
                    s("This is ${bold("bold")}. you know say?")
                    s("This is ${color("color", Color.RED)}. beautiful")
                    s("${bold(color("Both bold and color", Color.BLUE))} can be applied")
                    s(color("${bold("Bold")} and not bold", Color.YELLOW))
                }
            }

            slide {
                title("Syntax Highlighting")
                code(
                    """
                        class Hoge {
                          fun hoge() = println("Hello world")
                        }
                    """.trimIndent()
                )
            }
        }
    }
}
