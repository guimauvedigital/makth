package dev.makth.lexers

import dev.makth.extensions.StringValue
import dev.makth.interfaces.Value
import dev.makth.lexers.AlgorithmLexer.SyntaxException
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.operations.OperationFactory
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.VariableFactory
import kotlin.js.JsExport

/**
 * Lexer for math expressions
 * @param content Content to parse
 */
@JsExport
class MathLexer(private val content: String) {

    // Errors

    /**
     * Exception thrown when a value is expected but not found
     */
    open class NoValueFoundException : SyntaxException("No value found")

    /**
     * Exception thrown when an unknown operator is found
     * @param operator Unknown operator
     */
    open class UnknownOperatorException(val operator: String) :
        SyntaxException("Unknown operator: $operator")

    // Constants

    object Constants {
        const val NUMBERS = "0123456789"
        const val VARIABLES =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΣσςϹϲΤτΥυΦφΧχΨψΩω_" + NUMBERS
    }

    // Parsing vars

    private var operators = ArrayList<String>()
    private var i = 0
    private var values = ArrayList<Value>()

    // Parse an expression

    /**
     * Parse an expression
     * @param content Content to parse
     * @return Parsed value
     */
    @Throws(SyntaxException::class)
    fun execute(context: Context): Value {
        // For each character of the string
        while (i < content.length) {
            // Do something with current character
            when (content[i]) {
                ' ' -> {}
                '"' -> parseString('"')
                '$' -> parseString('$')
                '(' -> parseBlock()
                in Constants.NUMBERS -> parseNumber()
                in Constants.VARIABLES -> parseVariable()
                else -> insertOperation(content[i].toString())
            }

            // Increment i
            i++
        }

        // Entire expression parsed, apply remaining values
        while (operators.isNotEmpty()) {
            // Create a token
            val value = createValue()
            if (value != null) {
                // Insert it into the list
                insertValue(value)
            }
        }

        // Return token
        if (values.count() == 1) {
            return values[0].compute(context)
        }

        throw NoValueFoundException()
    }

    // Parse something

    private fun parseString(delimiter: Char) {
        val string = StringBuilder()
        i++

        // Get all the characters
        while (i < content.count() && content[i] != delimiter) {
            // Check for \
            if (content[i] == '\\' && i < content.count() - 1) {
                // Skip it and get next character
                i++
            }

            // Add character to string
            string.append(content[i])
            i++
        }

        // Insert the value
        insertValue(StringValue(string.toString(), delimiter == '$'))
    }

    private fun parseBlock() {
        // Some vars
        i++
        val subContent = StringBuilder()
        var count = 0

        // Get text until its closing brace
        while (i < content.length && (content[i] != ')' || count != 0)) {
            // Add current
            subContent.append(content[i])

            // Check for another opening/closing brace
            when (content[i]) {
                '(' -> count++
                ')' -> count--
            }

            // Increment i
            i++
        }

        // Insert the value
        insertValue(MathLexer(subContent.toString()).execute(Context()))
    }

    private fun parseNumber() {
        var value = 0L
        var powerOfTen = 0L

        // Get other digits
        while (i < content.length && content[i] in Constants.NUMBERS) {
            value = value * 10 + content[i].toString().toLong()
            i++
        }

        // If we have a dot
        if (i < content.length - 1 && content[i] == '.') {
            // Pass the dot
            i++

            // And start getting numbers after the dot
            while (i < content.length && content[i] in Constants.NUMBERS) {
                value = value * 10 + content[i].toString().toLong()
                i++
                powerOfTen++
            }
        }

        // Insert into values
        if (powerOfTen > 0) {
            TODO("Power not implemented!")
            // insertValue(Fraction(Number(`val`), Power(Number(10), Number(powerOfTen))))
        } else {
            insertValue(IntegerFactory.instantiate(value))
        }

        // Remove one, else current character is skipped
        i--
    }

    private fun parseVariable() {
        // Check name
        val name = StringBuilder()
        name.append(content[i])
        while (i < content.length - 1 && Constants.VARIABLES.contains(content[i + 1])) {
            // Add character to function name
            name.append(content[i + 1])

            // Increment j to continue
            i++
        }

        // Insert into values
        insertValue(VariableFactory.instantiate(name.toString()))
    }

    // Utils for parsing

    private fun insertValue(value: Value) {
        // Insert the value in the list
        values.add(0, value)
    }

    @Throws(SyntaxException::class)
    private fun insertOperation(op: String) {
        // While first operation has same of greater precedence to current, apply to two first
        // values
        while (operators.isNotEmpty() &&
            OperationFactory.getPrecedence(operators[0]) >= OperationFactory.getPrecedence(op)
        ) {
            // Create a token
            val value = createValue()
            if (value != null) {
                // Insert it into the list
                insertValue(value)
            }
        }

        // If subtraction with no number before
        if (op == "-" && values.isEmpty()) {
            insertValue(IntegerFactory.instantiate(0))
        }

        // If next is "="
        if (i < content.length - 1 && content[i + 1] == '=') {
            // Add it
            operators.add(0, "$op=")

            // Increment i
            i++
        } else {
            // Add current operation
            operators.add(0, op)
        }
    }

    @Throws(SyntaxException::class)
    private fun createValue(): Value? {
        // Get tokens
        val right = getFirstTokenAndRemove()
        val left = getFirstTokenAndRemove()

        // Get operator and apply
        val op = getFirstOperationAndRemove()
        return if (op != null) {
            OperationFactory.initialize(op, left, right)
        } else null
    }

    @Throws(SyntaxException::class)
    private fun getFirstTokenAndRemove(): Value {
        // Check if exists
        if (values.isNotEmpty()) {
            return values.removeAt(0)
        }
        throw NoValueFoundException()
    }

    private fun getFirstOperationAndRemove(): String? {
        // Check if first
        if (operators.isNotEmpty()) {
            return operators.removeAt(0)
        }

        // Nothing found
        return null
    }
}
