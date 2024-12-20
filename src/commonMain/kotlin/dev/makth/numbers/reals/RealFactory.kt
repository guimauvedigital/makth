package dev.makth.numbers.reals

import dev.makth.numbers.integers.IntegerFactory
import kotlin.math.floor

object RealFactory {

    /**
     * Pi constant
     */
    val pi: Real = RealImplPi()

    /**
     * Instantiate a real from a double value
     * @param value Double value
     * @return Real
     */
    fun instantiate(value: Double): Real {
        // Check if value is an integer
        if (floor(value) == value) {
            return IntegerFactory.instantiate(value.toLong())
        }

        // Check if value is a rational
        // Is it possible, as a double has a finite number of digits?

        // Check for some constants
        if (value == pi.doubleValue) {
            return pi
        }

        // Otherwise, it's a real
        return RealImpl(value)
    }

}
