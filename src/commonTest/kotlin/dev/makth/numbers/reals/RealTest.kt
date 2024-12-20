package dev.makth.numbers.reals

import dev.makth.extensions.StringValue
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.numbers.rationals.RationalFactory
import dev.makth.sets.matrixes.MatrixFactory
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RealTest {

    @Test
    fun rawString() {
        assertEquals(1.23456789.toString(), RealFactory.instantiate(1.23456789).rawString)
    }

    @Test
    fun laTeXString() {
        assertEquals(1.23456789.toString(), RealFactory.instantiate(1.23456789).laTeXString)
    }

    @Test
    fun absoluteValue() {
        assertEquals(
            RealFactory.instantiate(1.23456789),
            RealFactory.instantiate(-1.23456789).absoluteValue
        )
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(true, RealFactory.instantiate(1.23456789).equals(RealFactory.instantiate(1.23456789)))
    }

    @Test
    fun testEqualsFalse() {
        assertEquals(false, RealFactory.instantiate(1.23456789).equals(RealFactory.instantiate(9.87654321)))
    }

    @Test
    fun testLessThanTrue() {
        assertEquals(true, RealFactory.instantiate(1.23456789).lessThan(RealFactory.instantiate(9.87654321)))
    }

    @Test
    fun testLessThanFalse() {
        assertEquals(false, RealFactory.instantiate(9.87654321).lessThan(RealFactory.instantiate(1.23456789)))
    }

    @Test
    fun sumCorrectNatural() {
        // pi + 1 = 1 + pi
        assertEquals(
            RealFactory.instantiate(1 + PI),
            RealFactory.pi.sum(IntegerFactory.instantiate(1))
        )
    }

    @Test
    fun sumCorrectInteger() {
        // pi + -1 = pi - 1
        assertEquals(
            RealFactory.instantiate(PI - 1),
            RealFactory.pi.sum(IntegerFactory.instantiate(-1))
        )
    }

    @Test
    fun sumCorrectRational() {
        // pi + 1/3 = 1/3 + pi
        assertEquals(
            RealFactory.instantiate(1.0 / 3.0 + PI),
            RealFactory.pi.sum(RationalFactory.instantiate(1, 3))
        )
    }

    @Test
    fun sumCorrectReal() {
        // pi + pi = 2pi
        assertEquals(
            RealFactory.instantiate(2 * PI),
            RealFactory.pi.sum(RealFactory.pi)
        )
    }

    @Test
    fun sumUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            RealFactory.pi.sum(StringValue("test"))
        }
    }

    @Test
    fun multiplyCorrectNatural() {
        // pi * 3 = 3pi
        assertEquals(
            RealFactory.instantiate(3 * PI),
            RealFactory.pi.multiply(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun multiplyCorrectInteger() {
        // pi * -3 = -3pi
        assertEquals(
            RealFactory.instantiate(-3 * PI),
            RealFactory.pi.multiply(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun multiplyCorrectRational() {
        // pi * 1/3 = pi/3
        assertEquals(
            RealFactory.instantiate(PI / 3),
            RealFactory.pi.multiply(RationalFactory.instantiate(1, 3))
        )
    }

    @Test
    fun multiplyCorrectReal() {
        // pi * pi = pi^2
        assertEquals(
            RealFactory.instantiate(PI * PI),
            RealFactory.pi.multiply(RealFactory.pi)
        )
    }

    @Test
    fun multiplyCorrectVector() {
        // pi * [[1, 2], [3, 4]] = [[pi, 2pi], [3pi, 4pi]]
        assertEquals(
            MatrixFactory.instantiate(
                listOf(
                    listOf(RealFactory.pi, RealFactory.pi.multiply(IntegerFactory.instantiate(2))),
                    listOf(
                        RealFactory.pi.multiply(IntegerFactory.instantiate(3)),
                        RealFactory.pi.multiply(IntegerFactory.instantiate(4))
                    )
                )
            ),
            RealFactory.pi.multiply(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                    )
                )
            )
        )
    }

    @Test
    fun multiplyUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            RealFactory.pi.multiply(StringValue("test"))
        }
    }

    @Test
    fun divideCorrectNatural() {
        // pi / 3 = pi/3
        assertEquals(
            RealFactory.instantiate(PI / 3),
            RealFactory.pi.divide(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun divideCorrectInteger() {
        // pi / -3 = -pi/3
        assertEquals(
            RealFactory.instantiate(-PI / 3),
            RealFactory.pi.divide(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun divideCorrectRational() {
        // pi / 1/3 = 3pi
        assertEquals(
            RealFactory.instantiate(3 * PI),
            RealFactory.pi.divide(RationalFactory.instantiate(1, 3))
        )
    }

    @Test
    fun divideCorrectReal() {
        // pi / pi = 1
        assertEquals(
            IntegerFactory.instantiate(1),
            RealFactory.pi.divide(RealFactory.pi)
        )
    }

    @Test
    fun divideUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            RealFactory.pi.divide(StringValue("test"))
        }
    }

    @Test
    fun remainderCorrectNatural() {
        // pi % 3 = pi%3
        assertEquals(
            RealFactory.instantiate(PI % 3),
            RealFactory.pi.remainder(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun remainderCorrectInteger() {
        // pi % -3 = pi%(-3)
        assertEquals(
            RealFactory.instantiate(PI % -3),
            RealFactory.pi.remainder(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun remainderCorrectRational() {
        // pi % 1/2 = pi%1/2
        assertEquals(
            RealFactory.instantiate(PI % 0.5),
            RealFactory.pi.remainder(RationalFactory.instantiate(1, 2))
        )
    }

    @Test
    fun remainderCorrectReal() {
        // pi % pi = 0
        assertEquals(
            IntegerFactory.instantiate(0),
            RealFactory.pi.remainder(RealFactory.pi)
        )
    }

    @Test
    fun remainderUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            RealFactory.pi.remainder(StringValue("test"))
        }
    }

    @Test
    fun raiseCorrectNatural() {
        // pi ^ 2 = pi^2
        assertEquals(
            RealFactory.instantiate(PI * PI),
            RealFactory.pi.raise(IntegerFactory.instantiate(2))
        )
    }

    @Test
    fun raiseCorrectInteger() {
        // pi ^ -2 = 1/(pi^2)
        assertEquals(
            RealFactory.instantiate(1 / (PI * PI)),
            RealFactory.pi.raise(IntegerFactory.instantiate(-2))
        )
    }

    @Test
    fun raiseCorrectRational() {
        // pi ^ 1/2 = sqrt(pi)
        assertEquals(
            RealFactory.instantiate(sqrt(PI)),
            RealFactory.pi.raise(RationalFactory.instantiate(1, 2))
        )
    }

    @Test
    fun raiseCorrectReal() {
        // pi ^ pi = pi^pi
        assertEquals(
            RealFactory.instantiate(PI.pow(PI)),
            RealFactory.pi.raise(RealFactory.pi)
        )
    }

    @Test
    fun raiseUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            RealFactory.pi.raise(StringValue("test"))
        }
    }

    @Test
    fun instantiatePi() {
        assertEquals(
            RealFactory.pi,
            RealFactory.instantiate(PI)
        )
    }

    @Test
    fun piRawString() {
        assertEquals("\u03C0", RealFactory.pi.rawString)
    }

    @Test
    fun piLaTeXString() {
        assertEquals("\\pi", RealFactory.pi.laTeXString)
    }

}
