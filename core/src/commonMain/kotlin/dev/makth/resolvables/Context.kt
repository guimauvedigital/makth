package dev.makth.resolvables

import dev.kaccelero.models.IContext
import dev.makth.exceptions.ExecutionException
import dev.makth.interfaces.Action
import dev.makth.interfaces.Output
import dev.makth.interfaces.Value
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Context class
 * @param data Variables in current memory
 * @param outputs Outputs list
 */
@JsExport
data class Context(
    val data: Map<String, Value> = mapOf(),
    val outputs: List<Output> = listOf(),
) : IContext {

    /**
     * Execute an action in this context
     * @param action Action to execute
     * @return New context after execution
     */
    @JsName("executeAction")
    @Throws(ExecutionException::class)
    fun execute(action: Action): Context {
        return action.execute(this)
    }

    /**
     * Execute a list of actions in this context
     * @param actions Actions to execute
     * @return New context after execution
     */
    @Throws(ExecutionException::class)
    fun execute(actions: List<Action>): Context {
        var context = this
        for (action in actions) {
            context = action.execute(context)
        }
        return context
    }

}
