package dev.makth.actions

import dev.makth.exceptions.ExecutionException
import dev.makth.exceptions.UnknownVariablesException
import dev.makth.extensions.StringValue
import dev.makth.interfaces.Action
import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import kotlin.js.JsExport
import kotlin.jvm.JvmStatic

/**
 * Action that prints values
 * @param values Values to print
 */
@JsExport
data class PrintAction(val values: List<Value>) : Action {

    companion object {

        /**
         * Handler for print action
         * @param args Arguments of the action
         * @return Action created from the arguments
         */
        @JvmStatic
        fun handler(args: List<Value>): Action {
            return PrintAction(args.toList())
        }
    }

    @Throws(ExecutionException::class)
    override fun execute(context: Context): Context {
        // Generate output
        val output =
            values.map {
                // Compute value
                val computed = it.compute(context)

                // Check for missing variables
                computed.variables.takeIf { it.isNotEmpty() }?.let {
                    throw UnknownVariablesException(this, context, it)
                }

                // Return
                computed
            } + listOf(StringValue("\n"))

        // Return the new context
        return Context(context.data, context.outputs + output)
    }

    override val algorithmString: String
        get() {
            return values.joinToString(", ", "print(", ")") { it.algorithmString }
        }
}
