package dev.makth.lexers

import dev.makth.extensions.BooleanValue
import dev.makth.extensions.StringValue
import dev.makth.lexers.AlgorithmLexer.SyntaxException
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.numbers.rationals.RationalFactory
import dev.makth.numbers.reals.RealFactory
import dev.makth.operations.*
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.VariableFactory
import dev.makth.sets.matrixes.MatrixFactory
import dev.makth.sets.vectors.VectorFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MathLexerTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2)
        )
    )

    @Test
    fun parseString() {
        assertEquals(StringValue("Hello world!"), MathLexer("\"Hello world!\"").execute(context))
    }

    @Test
    fun parseStringWithEscape() {
        assertEquals(StringValue("Hello \"world\"!"), MathLexer("\"Hello \\\"world\\\"!\"").execute(context))
    }

    @Test
    fun parseLaTeXString() {
        assertEquals(StringValue("x ^ 2", true), MathLexer("\$x ^ 2\$").execute(context))
    }

    @Test
    fun parseLaTeXStringWithEscape() {
        assertEquals(StringValue("x \$ 2", true), MathLexer("\$x \\\$ 2\$").execute(context))
    }

    @Test
    fun parseBooleanTrue() {
        assertEquals(BooleanValue(true), MathLexer("true").execute(context))
    }

    @Test
    fun parseBooleanFalse() {
        assertEquals(BooleanValue(false), MathLexer("false").execute(context))
    }

    @Test
    fun parseVariable() {
        assertEquals(VariableFactory.instantiate("x"), MathLexer("x").execute(context))
    }

    @Test
    fun parsePi() {
        assertEquals(RealFactory.pi, MathLexer("pi").execute(context))
        assertEquals(RealFactory.pi, MathLexer("\u03C0").execute(context))
    }

    @Test
    fun parseInteger() {
        assertEquals(IntegerFactory.instantiate(2), MathLexer("2").execute(context))
    }

    @Test
    fun parseInteger2() {
        assertEquals(IntegerFactory.instantiate(-2), MathLexer("-2").execute(context))
    }

    @Test
    fun parseInteger3() {
        assertEquals(IntegerFactory.instantiate(-2), MathLexer("(-2)").execute(context))
    }

    @Test
    fun parseSum() {
        assertEquals(IntegerFactory.instantiate(3), MathLexer("1 + 2").execute(context))
    }

    @Test
    fun parseSum2() {
        assertEquals(IntegerFactory.instantiate(-1), MathLexer("1 + (-2)").execute(context))
    }

    @Test
    fun parseSumWithVariable() {
        assertEquals(
            Sum(IntegerFactory.instantiate(1), VariableFactory.instantiate("x")),
            MathLexer("1 + x").execute(context)
        )
    }

    @Test
    fun parseSumWithVariableAndContext() {
        assertEquals(
            IntegerFactory.instantiate(3),
            MathLexer("1 + x").execute(contextWithX)
        )
    }

    @Test
    fun parseDifference() {
        assertEquals(IntegerFactory.instantiate(-1), MathLexer("1 - 2").execute(context))
    }

    @Test
    fun parseDifferenceWithVariable() {
        assertEquals(
            Sum(
                IntegerFactory.instantiate(1),
                Product(IntegerFactory.instantiate(-1), VariableFactory.instantiate("x"))
            ),
            MathLexer("1 - x").execute(context)
        )
    }

    @Test
    fun parseDifferenceWithVariableAndContext() {
        assertEquals(
            IntegerFactory.instantiate(-1),
            MathLexer("1 - x").execute(contextWithX)
        )
    }

    @Test
    fun parseProduct() {
        assertEquals(IntegerFactory.instantiate(6), MathLexer("2 * 3").execute(context))
    }

    @Test
    fun parseProduct2() {
        assertEquals(IntegerFactory.instantiate(-6), MathLexer("2 * (-3)").execute(context))
    }

    @Test
    fun parseProductWithVariable() {
        assertEquals(
            Product(IntegerFactory.instantiate(2), VariableFactory.instantiate("x")),
            MathLexer("2 * x").execute(context)
        )
    }

    @Test
    fun parseProductWithVariableAndContext() {
        assertEquals(
            IntegerFactory.instantiate(4),
            MathLexer("2 * x").execute(contextWithX)
        )
    }

    @Test
    fun parseQuotient() {
        assertEquals(RationalFactory.instantiate(2, 3), MathLexer("2 / 3").execute(context))
    }

    @Test
    fun parseQuotient2() {
        assertEquals(RationalFactory.instantiate(-2, 3), MathLexer("2 / (-3)").execute(context))
    }

    @Test
    fun parseQuotientWithVariable() {
        assertEquals(
            Quotient(IntegerFactory.instantiate(2), VariableFactory.instantiate("x")),
            MathLexer("2 / x").execute(context)
        )
    }

    @Test
    fun parseQuotientWithVariableAndContext() {
        assertEquals(
            IntegerFactory.instantiate(1),
            MathLexer("2 / x").execute(contextWithX)
        )
    }

    @Test
    fun parseRemainder() {
        assertEquals(IntegerFactory.instantiate(2), MathLexer("2 % 3").execute(context))
    }

    @Test
    fun parseRemainder2() {
        assertEquals(IntegerFactory.instantiate(2), MathLexer("2 % (-3)").execute(context))
    }

    @Test
    fun parseRemainderWithVariable() {
        assertEquals(
            Remainder(IntegerFactory.instantiate(2), VariableFactory.instantiate("x")),
            MathLexer("2 % x").execute(context)
        )
    }

    @Test
    fun parseRemainderWithVariableAndContext() {
        assertEquals(
            IntegerFactory.instantiate(0),
            MathLexer("2 % x").execute(contextWithX)
        )
    }

    @Test
    fun parseExponentiate() {
        assertEquals(IntegerFactory.instantiate(8), MathLexer("2 ^ 3").execute(context))
    }

    @Test
    fun parseExponentiate2() {
        assertEquals(RationalFactory.instantiate(1, 8), MathLexer("2 ^ (-3)").execute(context))
    }

    @Test
    fun parseExponentiateWithVariable() {
        assertEquals(
            Exponentiation(IntegerFactory.instantiate(2), VariableFactory.instantiate("x")),
            MathLexer("2 ^ x").execute(context)
        )
    }

    @Test
    fun parseExponentiateWithVariableAndContext() {
        assertEquals(
            IntegerFactory.instantiate(4),
            MathLexer("2 ^ x").execute(contextWithX)
        )
    }

    @Test
    fun parseExpressionWithParentheses() {
        assertEquals(
            IntegerFactory.instantiate(9),
            MathLexer("(1 + 2) * (9 / (1 + 2))").execute(context)
        )
    }

    @Test
    fun parseExpressionWithoutParentheses() {
        assertEquals(
            IntegerFactory.instantiate(26),
            MathLexer("2 * 3 + 4 * 5").execute(context)
        )
    }

    @Test
    fun parseEquality() {
        assertEquals(
            Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2)),
            MathLexer("x = 2").execute(context)
        )
    }

    @Test
    fun parseEqualityWithVariable() {
        assertEquals(
            BooleanValue(true),
            MathLexer("x = 2").execute(contextWithX)
        )
    }

    @Test
    fun parseEqualityNotEquals() {
        assertEquals(
            Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2), Equality.Operator.NotEquals),
            MathLexer("x != 2").execute(context)
        )
    }

    @Test
    fun parseEqualityLessThan() {
        assertEquals(
            Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2), Equality.Operator.LessThan),
            MathLexer("x < 2").execute(context)
        )
    }

    @Test
    fun parseEqualityGreaterThan() {
        assertEquals(
            Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2), Equality.Operator.GreaterThan),
            MathLexer("x > 2").execute(context)
        )
    }

    @Test
    fun parseEqualityLessThanOrEquals() {
        assertEquals(
            Equality(
                VariableFactory.instantiate("x"),
                IntegerFactory.instantiate(2),
                Equality.Operator.LessThanOrEquals
            ),
            MathLexer("x <= 2").execute(context)
        )
    }

    @Test
    fun parseEqualityGreaterThanOrEquals() {
        assertEquals(
            Equality(
                VariableFactory.instantiate("x"),
                IntegerFactory.instantiate(2),
                Equality.Operator.GreaterThanOrEquals
            ),
            MathLexer("x >= 2").execute(context)
        )
    }

    @Test
    fun parseVector() {
        assertEquals(
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ),
            MathLexer("(1; 2; 3)").execute(context)
        )
    }

    @Test
    fun parseMatrix() {
        assertEquals(
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2), IntegerFactory.instantiate(3)),
                    listOf(IntegerFactory.instantiate(4), IntegerFactory.instantiate(5), IntegerFactory.instantiate(6)),
                    listOf(IntegerFactory.instantiate(7), IntegerFactory.instantiate(8), IntegerFactory.instantiate(9))
                )
            ),
            MathLexer("(1, 2, 3; 4, 5, 6; 7, 8, 9)").execute(context)
        )
    }

    @Test
    fun parseSyntaxException() {
        assertFailsWith(SyntaxException::class) {
            MathLexer("1 +").execute(context)
        }
        assertFailsWith(SyntaxException::class) {
            MathLexer("3 * 1 + 2)").execute(context)
        }
        assertFailsWith(SyntaxException::class) {
            MathLexer("* 2").execute(context)
        }
        assertFailsWith(SyntaxException::class) {
            MathLexer("1 2").execute(context)
        }
        assertFailsWith(SyntaxException::class) {
            MathLexer("()").execute(context)
        }
    }

}
