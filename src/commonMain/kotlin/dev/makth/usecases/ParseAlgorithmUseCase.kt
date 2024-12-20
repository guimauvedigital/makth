package dev.makth.usecases

import dev.makth.interfaces.Action
import dev.makth.interfaces.Value
import dev.makth.lexers.AlgorithmLexer
import kotlin.js.JsExport

@JsExport
class ParseAlgorithmUseCase : IParseAlgorithmUseCase {

    override fun invoke(input1: String, input2: Map<String, (List<Value>) -> Action>): List<Action> {
        return AlgorithmLexer(input1).registerKeywords(input2).execute()
    }

}
