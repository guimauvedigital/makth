package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Real
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.extensions.orOrThrowUnsupportedOperationException

/**
 * Quotient operation.
 * @param left Left value
 * @param right Right value
 */
data class Quotient(
    val left: Value,
    val right: Value
) : Operation {

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)
        return try {
            left.divide(right)
        } catch (e: UnsupportedOperationException) {
            Quotient(left, right)
        }
    }

    override fun toRawString(): String {
        val leftRawString = left.toRawString().let {
            if (left.getMainPrecedence() < getMainPrecedence()) "($it)" else it
        }
        val rightRawString = right.toRawString().let {
            if (right.getMainPrecedence() < getMainPrecedence()) "($it)" else it
        }
        return "$leftRawString / $rightRawString"
    }

    override fun toLaTeXString(): String {
        // We don't need to add parentheses here because it's useless with '\frac{}{}'
        return "\\frac{${left.toLaTeXString()}}{${right.toLaTeXString()}}"
    }

    override fun extractVariables(): Set<Variable> {
        return left.extractVariables() + right.extractVariables()
    }

    override fun getMainPrecedence(): Int {
        return Operation.Utils.getPrecedence("/")
    }

    override fun equals(right: Value): Boolean {
        if (right is Quotient) {
            return orOrThrowUnsupportedOperationException(
                { left.equals(right.left) && this.right.equals(right.right) },
                { left.equals(this.right) && right.left.equals(right.right) }
            )
        }
        return super.equals(right)
    }

}
