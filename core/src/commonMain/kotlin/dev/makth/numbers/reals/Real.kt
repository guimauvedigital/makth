package dev.makth.numbers.reals

import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import dev.makth.resolvables.variables.Variable
import dev.makth.sets.matrixes.Matrix
import dev.makth.sets.matrixes.MatrixFactory
import dev.makth.sets.vectors.Vector
import kotlin.js.JsExport
import kotlin.math.abs
import kotlin.math.pow

/**
 * Real representation
 */
@JsExport
interface Real : Vector {

    // Real interface

    /**
     * Get double value
     */
    val doubleValue: Double

    /**
     * Get the absolute value of this real
     */
    val absoluteValue: Real
        get() = RealFactory.instantiate(abs(doubleValue))

    // Vector

    override val elements: List<Value>
        get() {
            return listOf(this)
        }

    // Value

    override val rawString: String
        get() {
            return doubleValue.toString()
        }

    override val laTeXString: String
        get() {
            return doubleValue.toString()
        }

    override fun compute(context: Context): Value {
        return this
    }

    override val variables: Set<Variable>
        get() {
            return setOf()
        }

    // Operations

    override fun equals(right: Value): Boolean {
        if (right is Real) {
            return doubleValue == right.doubleValue
        }
        return super.equals(right)
    }

    override fun lessThan(right: Value): Boolean {
        if (right is Real) {
            return doubleValue < right.doubleValue
        }
        return super.lessThan(right)
    }

    override fun sum(right: Value): Value {
        if (right is Real) {
            return RealFactory.instantiate(doubleValue + right.doubleValue)
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Real) {
            return RealFactory.instantiate(doubleValue * right.doubleValue)
        }
        if (right is Matrix) {
            return MatrixFactory.instantiate(right.rows.map { rows ->
                rows.map { multiply(it) }
            })
        }
        return super.multiply(right)
    }

    override fun divide(right: Value): Value {
        if (right is Real) {
            return RealFactory.instantiate(doubleValue / right.doubleValue)
        }
        return super.divide(right)
    }

    override fun remainder(right: Value): Value {
        if (right is Real) {
            return RealFactory.instantiate(doubleValue % right.doubleValue)
        }
        return super.remainder(right)
    }

    override fun raise(right: Value): Value {
        if (right is Real) {
            return RealFactory.instantiate(doubleValue.pow(right.doubleValue))
        }
        return super.raise(right)
    }

}
