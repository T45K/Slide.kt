package io.github.t45k.slidekt.api

data class Color(val red: Int, val green: Int, val blue: Int) {
    override fun toString(): String = "${red.toTwoDigitsHexString()}${green.toTwoDigitsHexString()}${blue.toTwoDigitsHexString()}"

    @OptIn(ExperimentalStdlibApi::class)
    private fun Int.toTwoDigitsHexString(): String = this.toHexString().let { it.substring(it.length - 2) }

    companion object {
        val WHITE = Color(255, 255, 255)
        val YELLOW = Color(255, 255, 0)
        val GRAY = Color(128, 128, 128)
        val RED = Color(255, 0, 0)
        val GREEN = Color(0, 255, 0)
        val BLUE = Color(0, 0, 255)
        val BLACK = Color(0, 0, 0)

        @OptIn(ExperimentalStdlibApi::class)
        fun fromRgbHex(str: String): Color = Color(
            str.substring(0..1).hexToInt(),
            str.substring(2..3).hexToInt(),
            str.substring(4..5).hexToInt(),
        )
    }
}
