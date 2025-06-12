package dev.makth.numbers.naturals

import kotlin.js.JsExport

@JsExport
object NaturalFactory {

    /**
     * Instantiate a natural from a long value
     * @param value Long value
     * @return Natural
     */
    fun instantiate(value: Long): Natural {
        return NaturalImpl(value)
    }

}
