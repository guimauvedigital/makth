package dev.makth.numbers.rationals

import dev.makth.numbers.integers.Integer
import dev.makth.numbers.naturals.Natural

internal data class RationalImpl(
    override val numerator: Integer,
    override val denominator: Natural,
) : Rational {

    init {
        if (denominator.longValue == 0L) {
            throw IllegalArgumentException("Denominator cannot be null!")
        }
    }

}
