package dev.makth.resolvables.variables

import dev.makth.extensions.BooleanValue
import dev.makth.interfaces.Value
import dev.makth.numbers.reals.RealFactory
import kotlin.js.JsExport

@JsExport
object VariableFactory {

    /**
     * Instantiate a variable from a name
     * @param name Variable name
     * @return Variable instance
     */
    fun instantiate(name: String): Value {
        // Check for booleans
        if (name == "true" || name == "false") {
            return BooleanValue(name == "true")
        }

        // Check for some constants
        if (name == "pi" || name == "\u03C0") {
            return RealFactory.pi
        }

        return VariableImpl(name)
    }

}
