package io.github.t45k.slidekt.engine.component

import io.github.t45k.slidekt.engine.component.DecoratedText.BoldText
import io.github.t45k.slidekt.engine.component.DecoratedText.CompoundText
import io.github.t45k.slidekt.engine.component.DecoratedText.SimpleText
import kotlin.test.Test
import kotlin.test.assertEquals

class TextBoxKtTest {
    @Test
    fun testParseDecoration() {
        assertEquals(
            CompoundText(
                SimpleText("a"),
                BoldText(
                    CompoundText(
                        SimpleText("bc"),
                        BoldText(SimpleText("d")),
                    )
                ),
                CompoundText(
                    SimpleText("e"),
                    BoldText(
                        SimpleText("f")
                    ),
                ),
            ),
            parseDecoration("a<bold>bc<bold>d</bold></bold>e<bold>f</bold>")
        )

        assertEquals(
            CompoundText(
                SimpleText("This is "),
                BoldText(SimpleText("bold")),
                SimpleText(". you know say?")
            ),
            parseDecoration("This is <bold>bold</bold>. you know say?"),
        )
    }
}
