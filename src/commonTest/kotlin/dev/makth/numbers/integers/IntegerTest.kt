package dev.makth.numbers.integers

import dev.makth.numbers.rationals.RationalFactory
import dev.makth.numbers.reals.RealFactory
import dev.makth.sets.matrixes.MatrixFactory
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class IntegerTest {

    @Test
    fun rawString() {
        assertEquals("-1", IntegerFactory.instantiate(-1).rawString)
    }

    @Test
    fun laTeXString() {
        assertEquals("-1", IntegerFactory.instantiate(-1).laTeXString)
    }

    @Test
    fun correctNumerator() {
        assertEquals(
            IntegerFactory.instantiate(-2),
            IntegerFactory.instantiate(-2).numerator
        )
    }

    @Test
    fun correctDenominator() {
        assertEquals(
            IntegerFactory.instantiate(1),
            IntegerFactory.instantiate(-2).denominator
        )
    }

    @Test
    fun sumCorrectNatural() {
        // -2 + 1 = -1
        assertEquals(IntegerFactory.instantiate(-1), IntegerFactory.instantiate(-2).sum(IntegerFactory.instantiate(1)))
    }

    @Test
    fun sumCorrectInteger() {
        // -2 + -1 = -3
        assertEquals(IntegerFactory.instantiate(-3), IntegerFactory.instantiate(-2).sum(IntegerFactory.instantiate(-1)))
    }

    @Test
    fun sumCorrectRational() {
        // -1 + 1/2 = -1/2
        assertEquals(
            RationalFactory.instantiate(-1, 2),
            IntegerFactory.instantiate(-1).sum(RationalFactory.instantiate(1, 2))
        )
    }

    @Test
    fun sumCorrectReal() {
        // -2 + pi = -2 + pi
        assertEquals(
            RealFactory.instantiate(-2 + PI),
            IntegerFactory.instantiate(-2).sum(RealFactory.pi)
        )
    }

    @Test
    fun multiplyCorrectNatural() {
        // -2 * 3 = -6
        assertEquals(
            IntegerFactory.instantiate(-6),
            IntegerFactory.instantiate(-2).multiply(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun multiplyCorrectInteger() {
        // -2 * -3 = 6
        assertEquals(
            IntegerFactory.instantiate(6),
            IntegerFactory.instantiate(-2).multiply(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun multiplyCorrectRational() {
        // -3 * 1/2 = -3/2
        assertEquals(
            RationalFactory.instantiate(-3, 2),
            IntegerFactory.instantiate(-3).multiply(RationalFactory.instantiate(1, 2))
        )
    }

    @Test
    fun multiplyCorrectReal() {
        // -2 * pi = -2 * pi
        assertEquals(
            RealFactory.instantiate(-2 * PI),
            IntegerFactory.instantiate(-2).multiply(RealFactory.pi)
        )
    }

    @Test
    fun multiplyCorrectMatrix() {
        // -2 * [[1, 2], [3, 4]] = [[-2, -4], [-6, -8]]
        assertEquals(
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(-2), IntegerFactory.instantiate(-4)),
                    listOf(IntegerFactory.instantiate(-6), IntegerFactory.instantiate(-8))
                )
            ),
            IntegerFactory.instantiate(-2).multiply(
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
        // -2 / 3 = -2/3
        assertEquals(
            RationalFactory.instantiate(-2, 3),
            IntegerFactory.instantiate(-2).divide(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun divideCorrectInteger() {
        // -2 / -3 = 2/3
        assertEquals(
            RationalFactory.instantiate(2, 3),
            IntegerFactory.instantiate(-2).divide(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun divideCorrectRational() {
        // -3 / (5/2) = -6/5
        assertEquals(
            RationalFactory.instantiate(-6, 5),
            IntegerFactory.instantiate(-3).divide(RationalFactory.instantiate(5, 2))
        )
    }

    @Test
    fun divideCorrectReal() {
        // -2 / pi = -2 / pi
        assertEquals(
            RealFactory.instantiate(-2 / PI),
            IntegerFactory.instantiate(-2).divide(RealFactory.pi)
        )
    }

    @Test
    fun remainderCorrectNatural() {
        // -5 % 3 = -2
        assertEquals(
            IntegerFactory.instantiate(-2),
            IntegerFactory.instantiate(-5).remainder(IntegerFactory.instantiate(3))
        )
    }

    @Test
    fun remainderCorrectInteger() {
        // -5 % -3 = -2
        assertEquals(
            IntegerFactory.instantiate(-2),
            IntegerFactory.instantiate(-5).remainder(IntegerFactory.instantiate(-3))
        )
    }

    @Test
    fun remainderCorrectRational() {
        // -2 % (3/2) = -1/2
        assertEquals(
            RationalFactory.instantiate(-1, 2),
            IntegerFactory.instantiate(-2).remainder(RationalFactory.instantiate(3, 2))
        )
    }

    @Test
    fun remainderCorrectReal() {
        // -4 % pi = -4%pi
        assertEquals(
            RealFactory.instantiate(-4 % PI),
            IntegerFactory.instantiate(-4).remainder(RealFactory.pi)
        )
    }

    @Test
    fun raiseCorrectNatural() {
        // -2 ^ 2 = 4
        assertEquals(
            IntegerFactory.instantiate(4),
            IntegerFactory.instantiate(-2).raise(IntegerFactory.instantiate(2))
        )
    }

    @Test
    fun raiseCorrectInteger() {
        // -2 ^ -2 = 1/4
        assertEquals(
            RationalFactory.instantiate(1, 4),
            IntegerFactory.instantiate(-2).raise(IntegerFactory.instantiate(-2))
        )
    }

    @Test
    fun raiseCorrectRational() {
        // -8 ^ (1/3) = -2
        assertEquals(
            IntegerFactory.instantiate(-2),
            IntegerFactory.instantiate(-8).raise(RationalFactory.instantiate(1, 3))
        )
    }

}
