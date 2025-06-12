package dev.makth.operations

import dev.makth.lexers.AlgorithmLexer.SyntaxException
import dev.makth.numbers.integers.IntegerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OperationsTest {

    @Test
    fun syntaxException() {
        assertFailsWith(SyntaxException::class) {
            OperationFactory.initialize("a", IntegerFactory.instantiate(1), IntegerFactory.instantiate(2))
        }
    }

    @Test
    fun precedenceExponentiateOverMultiply() {
        assertTrue(
            OperationFactory.getPrecedence("^") > OperationFactory.getPrecedence("*")
        )
    }

    @Test
    fun precedenceMultiplyOverSum() {
        assertTrue(
            OperationFactory.getPrecedence("*") > OperationFactory.getPrecedence("+")
        )
    }

    @Test
    fun precedenceSumOverEquals() {
        assertTrue(
            OperationFactory.getPrecedence("+") > OperationFactory.getPrecedence("=")
        )
    }

    @Test
    fun precedenceSumSameAsDifference() {
        assertEquals(OperationFactory.getPrecedence("+"), OperationFactory.getPrecedence("-"))
    }

    @Test
    fun precedenceMultiplySameAsDivide() {
        assertEquals(OperationFactory.getPrecedence("*"), OperationFactory.getPrecedence("/"))
    }

    @Test
    fun precedenceMultiplySameAsRemainder() {
        assertEquals(OperationFactory.getPrecedence("*"), OperationFactory.getPrecedence("%"))
    }

    @Test
    fun precedenceNotEqualsSameAsEquals() {
        assertEquals(OperationFactory.getPrecedence("!="), OperationFactory.getPrecedence("="))
    }

    @Test
    fun precedenceLessThanSameAsEquals() {
        assertEquals(OperationFactory.getPrecedence("<"), OperationFactory.getPrecedence("="))
    }

    @Test
    fun precedenceGreaterThanSameAsEquals() {
        assertEquals(OperationFactory.getPrecedence(">"), OperationFactory.getPrecedence("="))
    }

    @Test
    fun precedenceLessThanOrEqualsSameAsEquals() {
        assertEquals(OperationFactory.getPrecedence("<="), OperationFactory.getPrecedence("="))
    }

    @Test
    fun precedenceGreaterThanOrEqualsSameAsEquals() {
        assertEquals(OperationFactory.getPrecedence(">="), OperationFactory.getPrecedence("="))
    }

}
