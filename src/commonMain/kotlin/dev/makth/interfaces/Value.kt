package dev.makth.interfaces

import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.Variable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Interface for all values that can be computed
 */
@JsExport
interface Value : Output {

    /**
     * Convert the value to a string that can be parsed by the algorithm lexer
     */
    val algorithmString: String
        get() {
            return rawString
        }

    /**
     * Compute the value in the given context
     * @param context Context of the value
     * @return Computed value
     */
    fun compute(context: Context): Value

    /**
     * Convert the value to a raw string
     */
    val rawString: String

    /**
     * Convert the value to a LaTeX string
     */
    val laTeXString: String

    /**
     * Extract all variables from the value
     */
    val variables: Set<Variable>

    /**
     * Get the precedence of the value, used for braces
     */
    val mainPrecedence: Int
        get() {
            return Int.MAX_VALUE
        }

    /**
     * Check if the value is equal to another value
     * @param right Right value
     * @return True if equals, false otherwise
     */
    @JsName("equalsValue")
    fun equals(right: Value): Boolean {
        throw UnsupportedOperationException()
    }

    /**
     * Check if the value is less than another value
     * @param right Right value
     * @return True if less than, false otherwise
     */
    fun lessThan(right: Value): Boolean {
        throw UnsupportedOperationException()
    }

    /**
     * Sum left value with right value
     * @param right Right value
     * @return Result
     */
    fun sum(right: Value): Value {
        throw UnsupportedOperationException()
    }

    /**
     * Multiply left value by right value
     * @param right Right value
     * @return Result
     */
    fun multiply(right: Value): Value {
        throw UnsupportedOperationException()
    }

    /**
     * Divide by another value
     * @param right Value to divide by
     * @return Result of the division
     */
    fun divide(right: Value): Value {
        throw UnsupportedOperationException()
    }

    /**
     * Get the remainder of the division by another value
     * @param right Value to divide by
     * @return Remainder of the division
     */
    fun remainder(right: Value): Value {
        throw UnsupportedOperationException()
    }

    /**
     * Raise left value to the power of right value
     * @param right Right value
     * @return Result
     */
    fun raise(right: Value): Value {
        throw UnsupportedOperationException()
    }

}
