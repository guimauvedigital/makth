package dev.makth.numbers.reals

import kotlin.math.PI

internal class RealImplPi : Real {

    override val doubleValue: Double
        get() {
            return PI
        }

    override val rawString: String
        get() {
            return "\u03C0"
        }

    override val laTeXString: String
        get() {
            return "\\pi"
        }

}
