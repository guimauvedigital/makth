package dev.makth.numbers.integers

import dev.makth.numbers.naturals.NaturalFactory
import kotlin.js.JsExport

@JsExport
object IntegerFactory {

    /**
     * Instantiate an integer from a long value
     * @param value Long value
     * @return Integer
     */
    fun instantiate(value: Long): Integer {
        return if (value < 0) IntegerImpl(value) else NaturalFactory.instantiate(value)
    }

}
