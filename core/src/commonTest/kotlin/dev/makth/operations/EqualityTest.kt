package dev.makth.operations

import dev.makth.extensions.BooleanValue
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.VariableFactory
import dev.makth.sets.matrixes.MatrixFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class EqualityTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2)
        )
    )

    @Test
    fun testEqualsRealTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(1),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun testEqualsRealFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun testEqualsTrueWithVariable() {
        assertEquals(
            BooleanValue(true),
            Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2), Equality.Operator.Equals).compute(
                contextWithX
            )
        )
    }

    @Test
    fun testEqualsMatrixTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                    )
                ),
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                    )
                ),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun testEqualsMatrixFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                    )
                ),
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(4), IntegerFactory.instantiate(3))
                    )
                ),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun equalsrawString() {
        assertEquals(
            "1 = 2",
            Equality(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2), Equality.Operator.Equals).rawString
        )
    }

    @Test
    fun equalslaTeXString() {
        assertEquals(
            "1 \\eq 2",
            Equality(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2), Equality.Operator.Equals).laTeXString
        )
    }

    @Test
    fun testNotEqualsRealTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testNotEqualsRealFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(1),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testNotEqualsMatrixTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                    )
                ),
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(4), IntegerFactory.instantiate(3))
                    )
                ),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testNotEqualsMatrixFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                    )
                ),
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                        listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                    )
                ),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun notEqualsRawString() {
        assertEquals(
            "1 != 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.NotEquals
            ).rawString
        )
    }

    @Test
    fun notEqualsLaTeXString() {
        assertEquals(
            "1 \\ne 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.NotEquals
            ).laTeXString
        )
    }

    @Test
    fun testLessThanTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.LessThan
            ).compute(context)
        )
    }

    @Test
    fun testLessThanFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(2),
                IntegerFactory.instantiate(1),
                Equality.Operator.LessThan
            ).compute(context)
        )
    }

    @Test
    fun testLessThanFalse2() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(2),
                IntegerFactory.instantiate(2),
                Equality.Operator.LessThan
            ).compute(context)
        )
    }

    @Test
    fun lessThanRawString() {
        assertEquals(
            "1 < 2",
            Equality(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2), Equality.Operator.LessThan).rawString
        )
    }

    @Test
    fun lessThanLaTeXString() {
        assertEquals(
            "1 \\lt 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.LessThan
            ).laTeXString
        )
    }

    @Test
    fun testGreaterThanTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(2),
                IntegerFactory.instantiate(1),
                Equality.Operator.GreaterThan
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThan
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanFalse2() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(2),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThan
            ).compute(context)
        )
    }

    @Test
    fun greaterThanRawString() {
        assertEquals(
            "1 > 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThan
            ).rawString
        )
    }

    @Test
    fun greaterThanLaTeXString() {
        assertEquals(
            "1 \\gt 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThan
            ).laTeXString
        )
    }

    @Test
    fun testLessThanOrEqualsTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.LessThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testLessThanOrEqualsTrue2() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(1),
                Equality.Operator.LessThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testLessThanOrEqualsFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(2),
                IntegerFactory.instantiate(1),
                Equality.Operator.LessThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun lessThanOrEqualsRawString() {
        assertEquals(
            "1 <= 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.LessThanOrEquals
            ).rawString
        )
    }

    @Test
    fun lessThanOrEqualsRaTeXString() {
        assertEquals(
            "1 \\le 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.LessThanOrEquals
            ).laTeXString
        )
    }

    @Test
    fun testGreaterThanOrEqualsTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(2),
                IntegerFactory.instantiate(1),
                Equality.Operator.GreaterThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanOrEqualsTrue2() {
        assertEquals(
            BooleanValue(true),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(1),
                Equality.Operator.GreaterThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanOrEqualsFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun greaterThanOrEqualsRawString() {
        assertEquals(
            "1 >= 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThanOrEquals
            ).rawString
        )
    }

    @Test
    fun greaterThanOrEqualsLaTeXString() {
        assertEquals(
            "1 \\ge 2",
            Equality(
                IntegerFactory.instantiate(1),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThanOrEquals
            ).laTeXString
        )
    }

}
