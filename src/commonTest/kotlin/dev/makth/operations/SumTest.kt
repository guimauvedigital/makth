package dev.makth.operations

import dev.makth.numbers.integers.IntegerFactory
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.VariableFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class SumTest {

    private val context = Context()

    @Test
    fun rawString() {
        assertEquals(
            "1 + 2",
            Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)).rawString
        )
    }

    @Test
    fun toRawStringWithMinus() {
        assertEquals(
            "1 - 2",
            Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(-2)).rawString
        )
    }

    @Test
    fun toRawStringWithBraces() {
        // This will technically never happen, but it's the only way to test it
        assertEquals(
            "(1 = 1) + (2 = 2)",
            Sum(
                Equality(IntegerFactory.instantiate(1), IntegerFactory.instantiate(1)),
                Equality(IntegerFactory.instantiate(2), IntegerFactory.instantiate(2))
            ).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "1 + 2",
            Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithMinus() {
        assertEquals(
            "1 - 2",
            Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(-2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        // This will technically never happen, but it's the only way to test it
        assertEquals(
            "(1 \\eq 1) + (2 \\eq 2)",
            Sum(
                Equality(IntegerFactory.instantiate(1), IntegerFactory.instantiate(1)),
                Equality(IntegerFactory.instantiate(2), IntegerFactory.instantiate(2))
            ).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")),
            Sum(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")).variables
        )
    }

    @Test
    fun sumNaturals() {
        // Check that a sum is computed correctly
        assertEquals(
            IntegerFactory.instantiate(3),
            Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)).compute(context)
        )
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(
            true,
            Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2)).equals(
                Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2))
            )
        )
    }

    @Test
    fun testEqualsTrue2() {
        assertEquals(
            true,
            Sum(IntegerFactory.instantiate(2), VariableFactory.instantiate("x")).equals(
                Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2))
            )
        )
    }

    @Test
    fun testEqualsFalse() {
        assertEquals(
            false,
            Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(1)).equals(
                Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2))
            )
        )
    }

    @Test
    fun testLessThanTrue() {
        assertEquals(
            true,
            Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(1)).lessThan(
                Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2))
            )
        )
    }

    @Test
    fun testLessThanTrue2() {
        assertEquals(
            true,
            Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(1)).lessThan(
                Sum(IntegerFactory.instantiate(2), VariableFactory.instantiate("x"))
            )
        )
    }

}
