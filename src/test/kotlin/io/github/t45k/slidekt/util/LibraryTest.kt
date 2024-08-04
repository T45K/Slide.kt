package io.github.t45k.slidekt.util

import dev.snipme.highlights.Highlights
import kotlin.test.Test

class LibraryTest {

    @Test
    fun test() {
        Highlights.default().apply {
            setCode("class Main {}")
            println(getCodeStructure())
            println(getHighlights())
        }
    }
}