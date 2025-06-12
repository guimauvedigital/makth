package dev.makth.operations

import dev.makth.interfaces.Value
import dev.makth.lexers.AlgorithmLexer
import dev.makth.lexers.MathLexer
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.sets.matrixes.Matrix
import dev.makth.sets.matrixes.MatrixFactory
import kotlin.js.JsExport

@JsExport
object OperationFactory {

    /**
     * Initialize an operation from an operator and two values
     * @param operator Operator
     * @param left Left value
     * @param right Right value
     * @return Operation
     */
    @Throws(AlgorithmLexer.SyntaxException::class)
    fun initialize(operator: String, left: Value, right: Value): Value {
        return when (operator) {
            "+" -> Sum(left, right)
            "-" -> Sum(left, Product(IntegerFactory.instantiate(-1), right))
            "*" -> Product(left, right)
            "/" -> Quotient(left, right)
            "%" -> Remainder(left, right)
            "^" -> Exponentiation(left, right)
            "=" -> Equality(left, right)
            "!=" -> Equality(left, right, Equality.Operator.NotEquals)
            "<" -> Equality(left, right, Equality.Operator.LessThan)
            ">" -> Equality(left, right, Equality.Operator.GreaterThan)
            "<=" -> Equality(left, right, Equality.Operator.LessThanOrEquals)
            ">=" -> Equality(left, right, Equality.Operator.GreaterThanOrEquals)
            "," -> createHorizontalMatrix(left, right)
            ";" -> createVerticalMatrix(left, right)
            else -> throw MathLexer.UnknownOperatorException(operator)
        }
    }

    /**
     * Get the precedence of an operation
     * @param operation Operation
     * @return Precedence
     */
    internal fun getPrecedence(operation: String): Int {
        return when (operation) {
            "^" -> 5
            "*", "/", "%" -> 4
            "+", "-" -> 3
            "," -> 2
            ";" -> 1
            else -> 0
        }
    }

    private fun createHorizontalMatrix(left: Value, right: Value): Matrix {
        return if (left is Matrix && left.rows.count() == 1) {
            // We add one element to the first and only row
            MatrixFactory.instantiate(
                listOf(
                    left.rows.first() + right
                )
            )
        } else {
            // Create a new matrix with two elements on the only row
            MatrixFactory.instantiate(
                listOf(
                    listOf(left), listOf(right)
                )
            )
        }
    }

    private fun createVerticalMatrix(left: Value, right: Value): Matrix {
        return if (left is Matrix && right is Matrix && right.rows.count() == 1) {
            // We add one row to the first matrix
            MatrixFactory.instantiate(left.rows + right.rows)
        } else {
            // Create a new matrix with two rows
            MatrixFactory.instantiate(
                listOf(
                    listOf(left), listOf(right)
                )
            )
        }
    }

}
