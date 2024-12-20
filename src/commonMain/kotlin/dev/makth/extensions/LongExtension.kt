package dev.makth.extensions

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

fun Long.gcd(b: Long): Long {
    // Binary GCD algorithm (faster than classic GCD)

    if (this == 0L) return b
    if (b == 0L) return this

    var u = abs(this)
    var v = abs(b)
    u = u shr u.countTrailingZeroBits()

    do {
        v = v shr v.countTrailingZeroBits()
        if (u > v) {
            val t = v
            v = u
            u = t
        }
        v -= u
    } while (v != 0L)

    return u shl (this or b).countTrailingZeroBits()
}

fun Long.pow(power: Long): Long {
    // Fast power algorithm

    var result = 1L
    var base = this
    var exponent = power

    while (exponent > 0) {
        if (exponent and 1L == 1L) {
            result *= base
        }
        exponent /= 2L
        base *= base
    }

    return result
}

fun Long.nthRoot(n: Long): Double {
    // Fast nth root algorithm
    // Can this be improved?
    // It seems that it does not work correctly: sqrt(4) is NOT 2.5
    /*
    var x = this.toDouble()
    var y = 1.0
    while (x - y > 0.000000000000001) {
        x = (1.0 / n) * (((n - 1) * x) + (this / x.pow((n - 1).toDouble())))
        y = (1.0 / n) * (((n - 1) * y) + (this / y.pow((n - 1).toDouble())))
    }
    return x
    */

    // This is a temporary fix
    // Because using default pow function makes sqrt(256) = 15.999999999999998
    val result = toDouble().pow(1.0 / n.toDouble())
    return if (abs(round(result) - result) < 0.00000001) {
        round(result)
    } else {
        result
    }
}
