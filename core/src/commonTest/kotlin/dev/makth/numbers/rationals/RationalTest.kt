package dev.makth.numbers.rationals

import dev.makth.numbers.integers.IntegerFactory
import dev.makth.numbers.reals.RealFactory
import dev.makth.sets.matrixes.MatrixFactory
import kotlin.math.PI
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RationalTest {

    @Test
    fun rawString() {
        assertEquals("-1/2", RationalFactory.instantiate(-1, 2).rawString)
    }

    @Test
    fun laTeXString() {
        assertEquals("\\frac{-1}{2}", RationalFactory.instantiate(-1, 2).laTeXString)
    }

    @Test
    fun nullDenominatorThrows() {
        assertFailsWith(IllegalArgumentException::class) {
            RationalFactory.instantiate(1, 0)
        }
    }

    @Test
    fun simplifyToRational() {
        // 2/4 = 1/2
        assertEquals(
            RationalFactory.instantiate(1, 2),
            RationalFactory.instantiate(2, 4)
        )
    }

    @Test
    fun simplifyToInteger() {
        // 4/2 = 2
        assertEquals(
            IntegerFactory.instantiate(2),
            RationalFactory.instantiate(4, 2)
        )
    }

    @Test
    fun absoluteValue() {
        assertEquals(
            RationalFactory.instantiate(1, 2),
            RationalFactory.instantiate(-1, 2).absoluteValue
        )
    }

    @Test
    fun sumCorrectNatural() {
        // 1/2 + 1 = 3/2
        assertEquals(
            RationalFactory.instantiate(3, 2),
            RationalFactory.instantiate(1, 2).sum(IntegerFactory.instantiate(1))
        )
    }

    @Test
    fun sumCorrectInteger() {
        // 1/2 + -1 = -1/2
        assertEquals(
            RationalFactory.instantiate(-1, 2),
            RationalFactory.instantiate(1, 2).sum(IntegerFactory.instantiate(-1))
        )
    }

    @Test
    fun sumCorrectRational() {
        // 1/2 + 1/3 = 5/6
        assertEquals(
            RationalFactory.instantiate(5, 6),
            RationalFactory.instantiate(1, 2).sum(RationalFactory.instantiate(1, 3))
        )
    }

    @Test
    fun sumCorrectReal() {
        // 1/2 + pi = 1/2 + pi
        assertEquals(
            RealFactory.instantiate(0.5 + PI),
            RationalFactory.instantiate(1, 2).sum(RealFactory.pi)
        )
    }

    @Test
    fun multiplyCorrectNatural() {
        // 1/2 * 3 = 3/2
        assertEquals(
            RationalFactory.instantiate(3, 2),
            RationalFactory.instantiate(1, 2).multiply(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun multiplyCorrectInteger() {
        // 1/2 * -3 = -3/2
        assertEquals(
            RationalFactory.instantiate(-3, 2),
            RationalFactory.instantiate(1, 2).multiply(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun multiplyCorrectRational() {
        // 1/2 * 1/3 = 1/6
        assertEquals(
            RationalFactory.instantiate(1, 6),
            RationalFactory.instantiate(1, 2).multiply(RationalFactory.instantiate(1, 3))
        )
    }

    @Test
    fun multiplyCorrectReal() {
        // 1/2 * pi = 1/2 * pi
        assertEquals(
            RealFactory.instantiate(0.5 * PI),
            RationalFactory.instantiate(1, 2).multiply(RealFactory.pi)
        )
    }

    @Test
    fun mutliplyCorrectMatrix() {
        // 1/2 * [[1, 2], [3, 4]] = [[1/2, 1], [3/2, 2]]
        assertEquals(
            MatrixFactory.instantiate(
                listOf(
                    listOf(RationalFactory.instantiate(1, 2), IntegerFactory.instantiate(1)),
                    listOf(RationalFactory.instantiate(3, 2), IntegerFactory.instantiate(2))
                )
            ),
            RationalFactory.instantiate(1, 2).multiply(
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
    fun divideCorrectNatural() {
        // 1/2 / 3 = 1/6
        assertEquals(
            RationalFactory.instantiate(1, 6),
            RationalFactory.instantiate(1, 2).divide(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun divideCorrectInteger() {
        // 1/2 / -3 = -1/6
        assertEquals(
            RationalFactory.instantiate(-1, 6),
            RationalFactory.instantiate(1, 2).divide(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun divideCorrectRational() {
        // 1/2 / 1/3 = 3/2
        assertEquals(
            RationalFactory.instantiate(3, 2),
            RationalFactory.instantiate(1, 2).divide(RationalFactory.instantiate(1, 3))
        )
    }

    @Test
    fun divideCorrectReal() {
        // 1/2 / pi = 1/2 * pi
        assertEquals(
            RealFactory.instantiate(0.5 / PI),
            RationalFactory.instantiate(1, 2).divide(RealFactory.pi)
        )
    }

    @Test
    fun remainderCorrectNatural() {
        // 5/2 % 2 = 1/2
        assertEquals(
            RationalFactory.instantiate(1, 2),
            RationalFactory.instantiate(5, 2).remainder(IntegerFactory.instantiate(2))
        )
    }

    @Test
    fun remainderCorrectInteger() {
        // 5/2 % -2 = 1/2
        assertEquals(
            RationalFactory.instantiate(1, 2),
            RationalFactory.instantiate(5, 2).remainder(IntegerFactory.instantiate(-2))
        )
    }

    @Test
    fun remainderCorrectRational() {
        // 5/2 % 1/3 = 1/6
        assertEquals(
            RationalFactory.instantiate(1, 6),
            RationalFactory.instantiate(5, 2).remainder(RationalFactory.instantiate(1, 3))
        )
    }

    @Test
    fun remainderCorrectReal() {
        // 7/2 % pi = 7/2%pi
        assertEquals(
            RealFactory.instantiate(3.5 % PI),
            RationalFactory.instantiate(7, 2).remainder(RealFactory.pi)
        )
    }

    @Test
    fun raiseCorrectNatural() {
        // 2/3 ^ 2 = 4/9
        assertEquals(
            RationalFactory.instantiate(4, 9),
            RationalFactory.instantiate(2, 3).raise(IntegerFactory.instantiate(2))
        )
    }

    @Test
    fun raiseCorrectInteger() {
        // 2/3 ^ -2 = 9/4
        assertEquals(
            RationalFactory.instantiate(9, 4),
            RationalFactory.instantiate(2, 3).raise(IntegerFactory.instantiate(-2))
        )
    }

    @Test
    fun raiseCorrectRational() {
        // 4/9 ^ 1/2 = 2/3
        assertEquals(
            RationalFactory.instantiate(2, 3),
            RationalFactory.instantiate(4, 9).raise(RationalFactory.instantiate(1, 2))
        )
    }

    @Test
    fun raiseCorrectReal() {
        // 1/2 ^ pi = 1/(2^pi)
        assertEquals(
            RealFactory.instantiate(1.0 / 2.0.pow(PI)),
            RationalFactory.instantiate(1, 2).raise(RealFactory.pi)
        )
    }

}
