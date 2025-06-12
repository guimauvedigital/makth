package dev.makth.operations

import dev.makth.numbers.integers.IntegerFactory
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.VariableFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class RemainderTest {

    private val context = Context()

    @Test
    fun rawString() {
        assertEquals(
            "1 % 2",
            Remainder(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)).rawString
        )
    }

    @Test
    fun toRawStringWithBraces() {
        assertEquals(
            "(1 + 2) % (3 + 4)",
            Remainder(
                Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                Sum(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
            ).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "1 % 2",
            Remainder(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        assertEquals(
            "(1 + 2) % (3 + 4)",
            Remainder(
                Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                Sum(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
            ).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")),
            Remainder(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")).variables
        )
    }

    @Test
    fun divideNaturals() {
        // Check that a remainder is computed correctly
        assertEquals(
            IntegerFactory.instantiate(1),
            Remainder(IntegerFactory.instantiate(3), IntegerFactory.instantiate(2)).compute(context)
        )
    }

}
