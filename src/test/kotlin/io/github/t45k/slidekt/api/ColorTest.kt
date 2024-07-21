package io.github.t45k.slidekt.api

import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTest {
    @Test
    fun testToString() {
        assertEquals("ff0000", Color.RED.toString())
    }
}
