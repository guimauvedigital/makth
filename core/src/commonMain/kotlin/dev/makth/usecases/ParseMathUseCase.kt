package dev.makth.usecases

import dev.makth.interfaces.Value
import dev.makth.lexers.MathLexer
import dev.makth.resolvables.Context
import kotlin.js.JsExport

@JsExport
class ParseMathUseCase : IParseMathUseCase {

    override fun invoke(input1: String, input2: Context): Value {
        return MathLexer(input1).execute(input2)
    }

}
