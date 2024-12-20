package dev.makth.sets.vectors

import dev.makth.interfaces.Value
import kotlin.js.JsExport

@JsExport
object VectorFactory {

    /**
     * Instantiate a vector from a list of elements
     * @param elements List of elements
     * @return Vector
     */
    fun instantiate(elements: List<Value>): Vector {
        if (elements.count() == 1 && elements.first() is Vector) {
            return elements.first() as Vector
        }
        return VectorImpl(elements)
    }

}
