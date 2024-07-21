package io.github.t45k.slidekt.util

import kotlin.test.Test
import kotlin.test.assertEquals

class IndicesOfKtTest {
    @Test
    fun test() {
        assertEquals(listOf(0, 1, 2), "aaa".indicesOf("a"))
        assertEquals(listOf(0, 5, 11), "abcdeabcfffabc".indicesOf("abc"))
    }
}
