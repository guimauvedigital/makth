package dev.makth.sets.vectors

import dev.makth.interfaces.Value

internal data class VectorImpl(
    override val elements: List<Value>,
) : Vector
