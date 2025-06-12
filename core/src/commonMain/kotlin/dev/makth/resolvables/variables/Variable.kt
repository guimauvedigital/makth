package dev.makth.resolvables.variables

import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import kotlin.js.JsExport

/**
 * Variable representation
 */
@JsExport
interface Variable : Value {

    /**
     * Variable name
     */
    val name: String

    override fun compute(context: Context): Value {
        return context.data[name] ?: this
    }

    override val rawString: String
        get() {
            return name
        }

    override val laTeXString: String
        get() {
            return name
        }

    override val variables: Set<Variable>
        get() {
            return setOf(this)
        }

    override fun equals(right: Value): Boolean {
        // If name is different, we don't know what's inside each so we can't compare
        if (right is Variable && name == right.name) {
            return true
        }
        return super.equals(right)
    }

}
