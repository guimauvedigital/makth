package dev.makth.operations

import dev.makth.numbers.integers.IntegerFactory
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.VariableFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductTest {

    private val context = Context()

    @Test
    fun rawString() {
        assertEquals(
            "1 * 2",
            Product(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)).rawString
        )
    }

    @Test
    fun toRawStringWithBraces() {
        assertEquals(
            "(1 + 2) * (3 + 4)",
            Product(
                Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                Sum(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
            ).rawString
        )
    }

    @Test
    fun toRawStringWithMinus() {
        assertEquals(
            "-x",
            Product(IntegerFactory.instantiate(-1), VariableFactory.instantiate("x")).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "1 \\times 2",
            Product(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        assertEquals(
            "(1 + 2) \\times (3 + 4)",
            Product(
                Sum(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                Sum(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
            ).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithMinus() {
        assertEquals(
            "-x",
            Product(IntegerFactory.instantiate(-1), VariableFactory.instantiate("x")).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")),
            Product(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")).variables
        )
    }

    @Test
    fun multiplyNaturals() {
        // Check that a product is computed correctly
        assertEquals(
            IntegerFactory.instantiate(6),
            Product(IntegerFactory.instantiate(2), IntegerFactory.instantiate(3)).compute(context)
        )
    }

}
