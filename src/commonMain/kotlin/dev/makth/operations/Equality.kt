package dev.makth.operations

import dev.makth.extensions.BooleanValue
import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.Variable
import kotlin.js.JsExport

/**
 * Equality operation.
 * @param left Left value
 * @param right Right value
 * @param operator Operator, default to Equals
 */
@JsExport
data class Equality(
    val left: Value,
    val right: Value,
    val operator: Operator = Operator.Equals,
) : Operation {

    /**
     * Available operators for equalities
     */
    enum class Operator {

        Equals, NotEquals, LessThan, GreaterThan, LessThanOrEquals, GreaterThanOrEquals;

        /**
         * Get the raw string for this operator
         */
        val rawString: String
            get() {
                return when (this) {
                    Equals -> "="
                    NotEquals -> "!="
                    LessThan -> "<"
                    GreaterThan -> ">"
                    LessThanOrEquals -> "<="
                    GreaterThanOrEquals -> ">="
                }
            }

        /**
         * Get the LaTeX string for this operator
         */
        val laTeXString: String
            get() {
                return when (this) {
                    Equals -> "\\eq"
                    NotEquals -> "\\ne"
                    LessThan -> "\\lt"
                    GreaterThan -> "\\gt"
                    LessThanOrEquals -> "\\le"
                    GreaterThanOrEquals -> "\\ge"
                }
            }

    }

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)

        return try {
            return BooleanValue(
                when (operator) {
                    Operator.Equals -> left.equals(right)
                    Operator.NotEquals -> !left.equals(right)
                    Operator.LessThan -> left.lessThan(right)
                    Operator.GreaterThan -> !(left.lessThan(right) || left.equals(right))
                    Operator.LessThanOrEquals -> left.lessThan(right) || left.equals(right)
                    Operator.GreaterThanOrEquals -> !left.lessThan(right)
                }
            )
        } catch (e: UnsupportedOperationException) {
            Equality(left, right, operator)
        }
    }

    override val rawString: String
        get() {
            return "${left.rawString} ${operator.rawString} ${right.rawString}"
        }

    override val laTeXString: String
        get() {
            return "${left.laTeXString} ${operator.laTeXString} ${right.laTeXString}"
        }

    override val variables: Set<Variable>
        get() {
            return left.variables + right.variables
        }

    override val mainPrecedence: Int
        get() {
            return OperationFactory.getPrecedence("=")
        }

    override fun equals(right: Value): Boolean {
        if (right is Equality) {
            return left.equals(right.left) && this.right.equals(right.right) && operator == right.operator
        }
        return super.equals(right)
    }

}
