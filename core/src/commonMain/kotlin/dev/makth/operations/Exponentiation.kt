package dev.makth.operations

import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.Variable
import kotlin.js.JsExport

/**
 * Exponentiation operation.
 * @param left Left value
 * @param right Right value
 */
@JsExport
data class Exponentiation(
    val left: Value,
    val right: Value,
) : Operation {

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)
        return try {
            left.raise(right)
        } catch (e: UnsupportedOperationException) {
            Exponentiation(left, right)
        }
    }

    override val rawString: String
        get() {
            val leftRawString = left.rawString.let {
                if (left.mainPrecedence < mainPrecedence) "($it)" else it
            }
            val rightRawString = right.rawString.let {
                if (right.mainPrecedence < mainPrecedence) "($it)" else it
            }
            return "$leftRawString ^ $rightRawString"
        }

    override val laTeXString: String
        get() {
            val leftLaTeXString = left.laTeXString.let {
                if (left.mainPrecedence < mainPrecedence) "($it)" else it
            }
            return "$leftLaTeXString^{${right.laTeXString}}"
        }

    override val variables: Set<Variable>
        get() {
            return left.variables + right.variables
        }

    override val mainPrecedence: Int
        get() {
            return OperationFactory.getPrecedence("^")
        }

    override fun equals(right: Value): Boolean {
        if (right is Exponentiation) {
            return left.equals(right.left) && this.right.equals(right.right)
        }
        return super.equals(right)
    }

}
