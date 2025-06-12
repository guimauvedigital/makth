package dev.makth.usecases

import dev.kaccelero.usecases.IPairUseCase
import dev.makth.interfaces.Action
import dev.makth.interfaces.Value
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
interface IParseAlgorithmUseCase : IPairUseCase<String, Map<String, (List<Value>) -> Action>, List<Action>> {

    @JsName("invokeDefault")
    operator fun invoke(input: String): List<Action> {
        return invoke(input, emptyMap())
    }

}
