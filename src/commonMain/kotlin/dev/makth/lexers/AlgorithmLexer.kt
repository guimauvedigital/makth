package dev.makth.lexers

import dev.makth.actions.IfAction
import dev.makth.actions.PrintAction
import dev.makth.actions.SetAction
import dev.makth.actions.WhileAction
import dev.makth.interfaces.Action
import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import kotlin.js.JsExport
import kotlin.reflect.KClass

/**
 * A lexer for algorithms
 * @param content Content of the algorithm
 */
@JsExport
class AlgorithmLexer(private val content: String) {

    // Errors

    /**
     * Base class for all syntax errors
     * @param message Error message
     */
    open class SyntaxException(message: String) : Exception(message)

    /**
     * Exception thrown when an unknown keyword is found
     * @param keyword Unknown keyword
     */
    open class UnknownKeywordException(val keyword: String) :
        SyntaxException("Unknown keyword: $keyword")

    /**
     * Exception thrown when an unexpected keyword is found
     * @param keyword Unexpected keyword
     */
    open class UnexpectedKeywordException(val keyword: String) :
        SyntaxException("Unexpected keyword: $keyword")

    /**
     * Exception thrown when an unexpected brace is found
     * @param character Unexpected brace
     */
    open class UnexpectedBraceException(val character: String) :
        SyntaxException("Unexpected brace: $character")

    /**
     * Exception thrown when an unexpected slash is found
     * @param character Unexpected slash
     */
    open class UnexpectedSlashException(val character: String) :
        SyntaxException("Unexpected slash: $character")

    /**
     * Exception thrown when an incorrect argument count is found
     * @param keyword Keyword
     * @param count Argument count
     * @param expected Expected argument count
     */
    open class IncorrectArgumentCountException(
        val keyword: String,
        val count: Int,
        val expected: Int,
    ) : SyntaxException("Incorrect argument count for $keyword, got $count, expected $expected")

    /**
     * Exception thrown when an incorrect argument type is found
     * @param keyword Keyword
     * @param value Argument value
     * @param expected Expected argument type
     */
    open class IncorrectArgumentTypeException(
        val keyword: String,
        val value: Value,
        val expected: KClass<out Value>,
    ) :
        SyntaxException(
            "Incorrect argument type for $keyword, got ${value.rawString}, expected $expected"
        )

    // Constants

    object Constants {
        const val CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_"
    }

    // Parsing vars

    private var lastKeyword: String? = null
    private var arguments = ArrayList<Value>()
    private var i = 0
    private var actions = ArrayList<Action>()

    // Keyword handlers

    private var keywordHandlers: Map<String, (List<Value>) -> Action> =
        mapOf(
            "if" to IfAction::handler,
            "print" to PrintAction::handler,
            "set" to SetAction::handler,
            "while" to WhileAction::handler
        )

    /**
     * Register a keyword handler
     * @param keyword Keyword to register
     * @param handler Handler to register
     * @return This lexer, with the new handler registered
     */
    fun registerKeyword(keyword: String, handler: (List<Value>) -> Action): AlgorithmLexer {
        keywordHandlers += mapOf(keyword to handler)
        return this
    }

    /**
     * Register multiple keyword handlers
     * @param keywords Keywords to register
     * @return This lexer, with the new handlers registered
     */
    fun registerKeywords(keywords: Map<String, (List<Value>) -> Action>): AlgorithmLexer {
        keywordHandlers += keywords
        return this
    }

    // Parse an algorithm

    /**
     * Parse an algorithm, and return a list of actions
     * @return List of actions
     */
    @Throws(SyntaxException::class)
    fun execute(): List<Action> {
        // For each character of the string
        while (i < content.length) {
            // Do something with current character
            when (content[i]) {
                '{' -> parseBlock()
                '(' -> parseArgs()
                '/' -> parseComment()
                in Constants.CHARACTERS -> parseKeyword()
            }

            // Increment i
            i++
        }

        // Return actions
        return actions
    }

    private fun createAction(): Action {
        // Check for the keyword
        val result =
            keywordHandlers[lastKeyword ?: "none"]?.invoke(arguments)
                ?: throw if (lastKeyword != null)
                    UnknownKeywordException(lastKeyword ?: "none")
                else UnexpectedBraceException("(")

        // Clear storage
        arguments.clear()
        lastKeyword = null

        // Return the result action
        return result
    }

    private fun parseBlock() {
        // Some vars
        i++
        val subContent = StringBuilder()
        var count = 0

        // Get text until its closing brace
        while (i < content.length && (content[i] != '}' || count != 0)) {
            // Add current
            subContent.append(content[i])

            // Check for another opening/closing brace
            when (content[i]) {
                '{' -> count++
                '}' -> count--
            }

            // Increment i
            i++
        }

        // Parse block
        val block =
            AlgorithmLexer(subContent.toString()).registerKeywords(keywordHandlers).execute()

        // Get last action
        when (val lastAction = actions.removeLastOrNull()) {
            is IfAction -> {
                when (lastKeyword) {
                    null -> actions.add(IfAction(lastAction.condition, block))
                    "else" -> actions.add(IfAction(lastAction.condition, lastAction.actions, block))
                    else -> throw UnexpectedKeywordException(lastKeyword ?: "none")
                }
            }

            is WhileAction -> {
                actions.add(WhileAction(lastAction.condition, block))
            }

            else -> throw UnexpectedBraceException("{")
        }
    }

    private fun parseArgs() {
        // Some vars
        i++
        val argument = StringBuilder()
        var count = 0

        // Get text until its closing brace
        while (i < content.length && (content[i] != ')' || count != 0)) {
            // Add current
            argument.append(content[i])

            // Check for special characters
            when (content[i]) {
                '(' -> count++
                ')' -> count--
                ',' -> {
                    argument.deleteAt(argument.count() - 1) // Remove ','
                    arguments.add(MathLexer(argument.toString().trim()).execute(Context()))
                    argument.clear()
                }
            }

            // Increment i
            i++
        }

        // Add last
        val lastArgument = argument.toString().trim()
        if (lastArgument.isNotEmpty()) {
            arguments.add(MathLexer(lastArgument).execute(Context()))
        }

        // Create an action with those args
        actions.add(createAction())
    }

    private fun parseKeyword() {
        val keyword = StringBuilder()

        // Get all the characters
        while (i < content.length && content[i] in Constants.CHARACTERS) {
            keyword.append(content[i])
            i++
        }

        // Save keyword
        if (lastKeyword == null) {
            lastKeyword = keyword.toString()
        } else {
            throw UnexpectedKeywordException(keyword.toString())
        }

        // Remove one, else current character is skipped
        i--
    }

    private fun parseComment() {
        // Check if it's a line comment
        if (i + 1 < content.length && content[i + 1] == '/') {
            // Skip until the end of the line
            while (i < content.length && content[i] != '\n') {
                i++
            }
        } else if (i + 1 < content.length && content[i + 1] == '*') {
            // Skip until the end of the comment
            while (i + 1 < content.length && (content[i] != '*' || content[i + 1] != '/')) {
                i++
            }
            i++ // Also skip the '*' (else '/' is not skipped)
        } else {
            // Syntax exception
            throw UnexpectedSlashException("/")
        }
    }

}
