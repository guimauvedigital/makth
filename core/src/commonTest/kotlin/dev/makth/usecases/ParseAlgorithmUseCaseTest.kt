package dev.makth.usecases

import dev.makth.actions.SetAction
import dev.makth.lexers.AlgorithmLexerTest
import dev.makth.numbers.integers.IntegerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseAlgorithmUseCaseTest {

    @Test
    fun testInvoke() {
        val useCase = ParseAlgorithmUseCase()
        assertEquals(
            listOf(
                SetAction("x", IntegerFactory.instantiate(2))
            ),
            useCase.invoke("set(x, 2)")
        )
    }

    @Test
    fun testInvokeWithCustomKeywords() {
        val useCase = ParseAlgorithmUseCase()
        assertEquals(
            listOf(
                AlgorithmLexerTest.CustomAction(IntegerFactory.instantiate(2))
            ),
            useCase.invoke("custom(2)", mapOf("custom" to AlgorithmLexerTest.CustomAction::handler))
        )
    }

}
