package io.github.t45k.slidekt.util

fun String.indicesOf(string: String, startIndex: Int = 0): List<Int> {
    val index = this.indexOf(string, startIndex)
    return if (index == -1) emptyList()
    else listOf(index) + this.indicesOf(string, startIndex = index + string.length)
}
