package dev.makth.actions

import dev.makth.exceptions.ExecutionException
import dev.makth.exceptions.UnknownVariablesException
import dev.makth.extensions.StringValue
import dev.makth.interfaces.Action
import dev.makth.interfaces.Value
import dev.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import dev.makth.lexers.AlgorithmLexer.IncorrectArgumentTypeException
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.Variable
import kotlin.js.JsExport
import kotlin.jvm.JvmStatic

/**
 * Action that sets a variable
 * @param identifier Identifier of the variable to set
 * @param value Value to set
 */
@JsExport
data class SetAction(val identifier: String, val value: Value) : Action {

    companion object {

        /**
         * Handler for set action
         * @param args Arguments of the action
         * @return Action created from the arguments
         */
        @JvmStatic
        fun handler(args: List<Value>): Action {
            if (args.count() != 2) {
                throw IncorrectArgumentCountException("set", args.count(), 2)
            }
            val identifier = when (val identifierValue = args[0]) {
                is Variable -> identifierValue.name
                is StringValue -> identifierValue.value
                else -> throw IncorrectArgumentTypeException("set", identifierValue, Variable::class)
            }
            return SetAction(identifier, args[1])
        }
    }

    @Throws(ExecutionException::class)
    override fun execute(context: Context): Context {
        // First, compute the value with the given context
        val valueToSet = value.compute(context)

        // Check if there are missing variables
        valueToSet.variables.takeIf { it.isNotEmpty() }?.let {
            throw UnknownVariablesException(this, context, it)
        }

        // Return the new context
        return Context(context.data + mapOf(identifier to valueToSet), context.outputs)
    }

    override val algorithmString: String
        get() {
            return "set($identifier, ${value.algorithmString})"
        }
}
