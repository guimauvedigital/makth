package dev.makth.numbers.naturals

import dev.makth.numbers.integers.Integer
import kotlin.js.JsExport

/**
 * Natural representation
 */
@JsExport
interface Natural : Integer {

    // Real

    override val absoluteValue: Natural
        get() {
            return this
        }

}
