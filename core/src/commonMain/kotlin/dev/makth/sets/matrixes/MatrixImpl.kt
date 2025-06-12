package dev.makth.sets.matrixes

import dev.makth.interfaces.Value

internal data class MatrixImpl(
    override val rows: List<List<Value>>,
) : Matrix {

    override val columns: List<List<Value>>
        get() {
            return (0 until rows.first().count()).map { i ->
                rows.map { it[i] }
            }
        }

}
