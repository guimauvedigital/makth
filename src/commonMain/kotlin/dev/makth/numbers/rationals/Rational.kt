package dev.makth.numbers.rationals

import dev.makth.interfaces.Value
import dev.makth.numbers.integers.Integer
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.numbers.naturals.Natural
import dev.makth.numbers.reals.Real
import dev.makth.numbers.reals.RealImpl
import dev.makth.sets.matrixes.Matrix
import dev.makth.sets.matrixes.MatrixFactory
import kotlin.js.JsExport

/**
 * Rational representation
 */
@JsExport
interface Rational : Real {

    // Rational interface

    /**
     * Get numerator
     */
    val numerator: Integer

    /**
     * Get denominator
     */
    val denominator: Natural

    // Real

    override val doubleValue: Double
        get() {
            return numerator.doubleValue / denominator.doubleValue
        }

    override val absoluteValue: Rational
        get() {
            return RationalFactory.instantiate(numerator.absoluteValue, denominator)
        }

    // Value

    override val rawString: String
        get() {
            return "${numerator.rawString}/${denominator.rawString}"
        }

    override val laTeXString: String
        get() {
            return "\\frac{${numerator.laTeXString}}{${denominator.laTeXString}}"
        }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Integer) {
            val newNumerator = numerator.sum(denominator.multiply(right))
            if (newNumerator is Integer) {
                return RationalFactory.instantiate(newNumerator, denominator)
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator)
                .sum(right.numerator.multiply(denominator))
            val newDenominator = denominator.multiply(right.denominator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return RationalFactory.instantiate(newNumerator, newDenominator)
            }
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Integer) {
            val newNumerator = numerator.multiply(right)
            if (newNumerator is Integer) {
                return RationalFactory.instantiate(newNumerator, denominator)
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.numerator)
            val newDenominator = denominator.multiply(right.denominator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return RationalFactory.instantiate(newNumerator, newDenominator)
            }
        }
        if (right is Real) {
            return RealImpl(doubleValue).multiply(right)
        }
        if (right is Matrix) {
            return MatrixFactory.instantiate(right.rows.map { rows ->
                rows.map { multiply(it) }
            })
        }
        return super.multiply(right)
    }

    override fun divide(right: Value): Value {
        if (right is Integer) {
            val newDenominator = denominator.multiply(right)
            if (newDenominator is Integer) {
                return RationalFactory.instantiate(
                    numerator,
                    newDenominator
                )
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator)
            val newDenominator = denominator.multiply(right.numerator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return RationalFactory.instantiate(
                    newNumerator,
                    newDenominator
                )
            }
        }
        return super.divide(right)
    }

    override fun remainder(right: Value): Value {
        if (right is Integer) {
            val newRight = denominator.multiply(right)
            val newNumerator = numerator.remainder(newRight)
            if (newRight is Integer && newNumerator is Integer) {
                return RationalFactory.instantiate(
                    newNumerator,
                    denominator
                )
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator).remainder(right.numerator.multiply(denominator))
            val newDenominator = denominator.multiply(right.denominator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return RationalFactory.instantiate(
                    newNumerator,
                    newDenominator
                )
            }
        }
        return super.remainder(right)
    }

    override fun raise(right: Value): Value {
        val denominator = denominator
        if (denominator != IntegerFactory.instantiate(1)) {
            val newNumerator = numerator.raise(right)
            val newDenominator = denominator.raise(right)
            return newNumerator.divide(newDenominator)
        }
        return super.raise(right)
    }

}
