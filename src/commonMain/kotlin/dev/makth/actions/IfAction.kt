package dev.makth.actions

import dev.makth.exceptions.ExecutionException
import dev.makth.exceptions.NotABooleanException
import dev.makth.exceptions.UnknownVariablesException
import dev.makth.extensions.BooleanValue
import dev.makth.extensions.indentedLines
import dev.makth.interfaces.Action
import dev.makth.interfaces.Value
import dev.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import dev.makth.resolvables.Context
import kotlin.js.JsExport
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Action that executes a list of actions if a condition is true,
 * or another list of actions otherwise.
 * @param condition Condition to check
 * @param actions Actions to execute if condition is true
 * @param elseActions Actions to execute if condition is false
 */
@JsExport
data class IfAction @JvmOverloads constructor(
    val condition: Value,
    val actions: List<Action>,
    val elseActions: List<Action> = listOf(),
) : Action {

    companion object {

        /**
         * Handler for if action
         * @param args List of arguments
         * @return Action created from arguments
         */
        @JvmStatic
        fun handler(args: List<Value>): Action {
            if (args.count() != 1) {
                throw IncorrectArgumentCountException("if", args.count(), 1)
            }
            return IfAction(args[0], listOf())
        }
    }

    @Throws(ExecutionException::class)
    override fun execute(context: Context): Context {
        // Eval condition
        val evaluatedCondition = condition.compute(context)

        // Check if there are missing variables
        evaluatedCondition.variables.takeIf { it.isNotEmpty() }?.let {
            throw UnknownVariablesException(this, context, it)
        }

        // Check if condition is a boolean
        if (evaluatedCondition !is BooleanValue) {
            throw NotABooleanException(this, context, evaluatedCondition)
        }

        // Execute if it is true
        return if (evaluatedCondition.value) {
            context.execute(actions)
        } else {
            context.execute(elseActions)
        }
    }

    override val algorithmString: String
        get() {
            val builder = StringBuilder()
            builder.append("if (")
            builder.append(condition.algorithmString)
            builder.append(") {")
            for (action in actions) {
                builder.append("\n")
                builder.append(action.algorithmString.indentedLines)
            }
            if (elseActions.isNotEmpty()) {
                builder.append("\n} else {")
                for (action in elseActions) {
                    builder.append("\n")
                    builder.append(action.algorithmString.indentedLines)
                }
            }
            builder.append("\n}")
            return builder.toString()
        }
}
