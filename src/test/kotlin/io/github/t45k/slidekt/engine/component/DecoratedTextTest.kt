package io.github.t45k.slidekt.engine.component

import io.github.t45k.slidekt.engine.component.DecoratedText.BoldText
import io.github.t45k.slidekt.engine.component.DecoratedText.CompoundText
import io.github.t45k.slidekt.engine.component.DecoratedText.SimpleText
import io.github.t45k.slidekt.engine.component.DecorationTag.BoldBeginTag
import io.github.t45k.slidekt.engine.component.DecorationTag.BoldEndTag
import kotlin.test.Test
import kotlin.test.assertEquals

class DecoratedTextTest {
    @Test
    fun testParseDecoration() {
        assertEquals(
            CompoundText(
                SimpleText("a"),
                CompoundText(
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
            ),
            parseDecoration("a<bold>bc<bold>d</bold></bold>e<bold>f</bold>")
        )

        assertEquals(
            CompoundText(
                SimpleText("This is "),
                CompoundText(
                    BoldText(SimpleText("bold")),
                    SimpleText(". you know say?"),
                ),
            ),
            parseDecoration("This is <bold>bold</bold>. you know say?"),
        )
    }

    @Test
    fun testFindMatchingEndTag() {
        assertEquals(6, findMatchingEndTagIndex("<bold></bold>", BoldBeginTag, BoldEndTag))
        assertEquals(9, findMatchingEndTagIndex("<bold>abc</bold>", BoldBeginTag, BoldEndTag))
        assertEquals(6, findMatchingEndTagIndex("<bold></bold><bold></bold><bold></bold>", BoldBeginTag, BoldEndTag))
        assertEquals(22, findMatchingEndTagIndex("<bold>a<bold>b</bold>c</bold>", BoldBeginTag, BoldEndTag))
    }
}
