package dev.makth.extensions

fun orOrThrowUnsupportedOperationException(
    vararg blocks: () -> Boolean,
): Boolean {
    val values = blocks.map {
        try {
            it()
        } catch (e: UnsupportedOperationException) {
            null
        }
    }
    if (values.all { it == null }) {
        throw UnsupportedOperationException()
    }
    return values.fold(false) { acc, b -> acc || (b ?: false) }
}
