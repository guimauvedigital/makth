package dev.makth.usecases

import dev.kaccelero.usecases.IPairUseCase
import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import kotlin.js.JsExport

@JsExport
interface IParseMathUseCase : IPairUseCase<String, Context, Value>
