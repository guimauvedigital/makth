package dev.makth.actions

import dev.makth.exceptions.UnknownVariablesException
import dev.makth.extensions.StringValue
import dev.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import dev.makth.lexers.AlgorithmLexer.IncorrectArgumentTypeException
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.operations.Sum
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.VariableFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SetActionTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2)
        )
    )

    private val contextWithXAndY = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2),
            "y" to IntegerFactory.instantiate(4)
        )
    )

    @Test
    fun algorithmString() {
        assertEquals(
            "set(x, 2)",
            SetAction("x", IntegerFactory.instantiate(2)).algorithmString
        )
    }

    @Test
    fun handlerWithVariable() {
        assertEquals(
            SetAction("x", IntegerFactory.instantiate(2)),
            SetAction.handler(listOf(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2)))
        )
    }

    @Test
    fun handlerWithString() {
        assertEquals(
            SetAction("x", IntegerFactory.instantiate(2)),
            SetAction.handler(listOf(StringValue("x"), IntegerFactory.instantiate(2)))
        )
    }

    @Test
    fun handlerWithWrongArgsCount() {
        assertFailsWith(IncorrectArgumentCountException::class) {
            SetAction.handler(listOf())
        }
    }

    @Test
    fun handlerWithWrongArgType() {
        assertFailsWith(IncorrectArgumentTypeException::class) {
            SetAction.handler(listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)))
        }
    }

    @Test
    fun setInteger() {
        assertEquals(
            contextWithX,
            context.execute(SetAction("x", IntegerFactory.instantiate(2)))
        )
    }

    @Test
    fun setIntegerWithVariable() {
        assertEquals(
            contextWithXAndY,
            contextWithX.execute(SetAction("y", Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2))))
        )
    }

    @Test
    fun setIntegerWithVariableWithoutContext() {
        assertFailsWith(UnknownVariablesException::class) {
            context.execute(SetAction("y", Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2))))
        }
    }

}
