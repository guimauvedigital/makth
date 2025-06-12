package dev.makth.sets.vectors

import dev.makth.interfaces.Value
import dev.makth.numbers.reals.Real
import dev.makth.operations.Sum
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.Variable
import dev.makth.sets.matrixes.Matrix
import kotlin.js.JsExport

/**
 * Vector representation
 * It's a set of elements of the same type
 * @param elements List of elements
 */
@JsExport
interface Vector : Matrix {

    // Vector interface

    /**
     * Get elements
     */
    val elements: List<Value>

    // Iterable

    override val iterator: Iterator<Value>
        get() {
            return elements.iterator()
        }

    // Value

    override fun compute(context: Context): Value {
        return VectorFactory.instantiate(elements.map { it.compute(context) })
    }

    override val rawString: String
        get() {
            return elements.joinToString("; ", "(", ")") { it.rawString }
        }

    override val laTeXString: String
        get() {
            return elements.joinToString(" \\\\ ", "\\begin{pmatrix} ", " \\end{pmatrix}") { it.laTeXString }
        }

    override val variables: Set<Variable>
        get() {
            return elements.flatMap { it.variables }.toSet()
        }

    // Matrix

    override val rows: List<List<Value>>
        get() {
            return elements.map { listOf(it) }
        }

    override val columns: List<List<Value>>
        get() {
            return listOf(this.elements)
        }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Vector) {
            if (elements.count() != right.elements.count()) {
                throw UnsupportedOperationException("Cannot sum vectors of different sizes")
            }
            return VectorFactory.instantiate(elements.zip(right.elements).map { pair ->
                Sum(pair.first, pair.second).compute(Context())
            })
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Real) {
            return VectorFactory.instantiate(elements.map { it.multiply(right) })
        }
        return super.multiply(right)
    }

}
