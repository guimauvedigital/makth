package dev.makth.numbers.naturals

internal data class NaturalImpl(
    override val longValue: Long,
) : Natural {

    init {
        if (longValue < 0) {
            throw IllegalArgumentException("Natural cannot be negative!")
        }
    }

}
