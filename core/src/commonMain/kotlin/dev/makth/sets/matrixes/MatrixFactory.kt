package dev.makth.sets.matrixes

import dev.makth.interfaces.Value
import dev.makth.sets.vectors.VectorFactory
import kotlin.js.JsExport

@JsExport
object MatrixFactory {

    /**
     * Instantiate a matrix from a list of rows
     * @param rows List of rows
     * @return Matrix
     */
    fun instantiate(rows: List<List<Value>>): Matrix {
        // Check that matrix is not empty and all rows have the same size
        if (rows.isEmpty() || rows.any { it.count() != rows.first().count() }) {
            throw IllegalArgumentException("Invalid matrix")
        }

        // If matrix is a vector
        if (rows.first().count() == 1) {
            return VectorFactory.instantiate(rows.flatten())
        }

        // Normal matrix
        return MatrixImpl(rows)
    }

}
