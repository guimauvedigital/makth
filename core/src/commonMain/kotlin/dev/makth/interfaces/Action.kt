package dev.makth.interfaces

import dev.makth.exceptions.ExecutionException
import dev.makth.resolvables.Context
import kotlin.js.JsExport

/**
 * Interface for all actions that can be executed
 */
@JsExport
interface Action {

    /**
     * Execute the action in the given context
     * @param context Context of the action
     * @return New context after the action was executed
     */
    @Throws(ExecutionException::class)
    fun execute(context: Context): Context

    /**
     * String representation of the action
     */
    val algorithmString: String

}
