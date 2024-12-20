package dev.makth.usecases

import dev.makth.numbers.integers.IntegerFactory
import dev.makth.resolvables.Context
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseMathUseCaseTest {

    private val contextWithX = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2)
        )
    )

    @Test
    fun testInvoke() {
        val useCase = ParseMathUseCase()
        assertEquals(
            IntegerFactory.instantiate(4),
            useCase("2 * x", contextWithX)
        )
    }

}
