package dev.makth.exceptions

import dev.makth.interfaces.Action
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.Variable
import kotlin.js.JsExport

/**
 * Exception thrown when one (or more) variable is not found
 * @param action Action that failed
 * @param context Context of the action
 * @param variable Variables that were not found
 */
@JsExport
open class UnknownVariablesException(
    action: Action,
    context: Context,
    val variables: Set<Variable>,
) : ExecutionException(
    action, context,
    "Unknown variable(s): ${variables.joinToString(", ") { it.name }}"
)
