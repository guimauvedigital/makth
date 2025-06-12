package dev.makth.sets.vectors

import dev.makth.extensions.StringValue
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.resolvables.variables.VariableFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VectorTest {

    @Test
    fun rawString() {
        assertEquals(
            "(1; 2; 3)",
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "\\begin{pmatrix} 1 \\\\ 2 \\\\ 3 \\end{pmatrix}",
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")),
            VectorFactory.instantiate(
                listOf(
                    VariableFactory.instantiate("x"),
                    VariableFactory.instantiate("y")
                )
            ).variables
        )
    }

    @Test
    fun elements() {
        assertEquals(
            listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2), IntegerFactory.instantiate(3)),
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).elements
        )
    }

    @Test
    fun rows() {
        assertEquals(
            listOf(
                listOf(IntegerFactory.instantiate(1)),
                listOf(IntegerFactory.instantiate(2)),
                listOf(IntegerFactory.instantiate(3))
            ),
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).rows
        )
    }

    @Test
    fun columns() {
        assertEquals(
            listOf(listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2), IntegerFactory.instantiate(3))),
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).columns
        )
    }

    @Test
    fun sumCorrectVector() {
        assertEquals(
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(5),
                    IntegerFactory.instantiate(7),
                    IntegerFactory.instantiate(9)
                )
            ),
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).sum(
                VectorFactory.instantiate(
                    listOf(
                        IntegerFactory.instantiate(4),
                        IntegerFactory.instantiate(5),
                        IntegerFactory.instantiate(6)
                    )
                )
            )
        )
    }

    @Test
    fun sumIncorrectVector() {
        assertFailsWith(UnsupportedOperationException::class) {
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).sum(
                VectorFactory.instantiate(listOf(IntegerFactory.instantiate(4), IntegerFactory.instantiate(5)))
            )
        }
    }

    @Test
    fun sumUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).sum(
                StringValue("test")
            )
        }
    }

    @Test
    fun multiplyCorrectReal() {
        assertEquals(
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(4),
                    IntegerFactory.instantiate(6)
                )
            ),
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).multiply(
                IntegerFactory.instantiate(2)
            )
        )
    }

    @Test
    fun multiplyUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            VectorFactory.instantiate(
                listOf(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(2),
                    IntegerFactory.instantiate(3)
                )
            ).multiply(
                StringValue("test")
            )
        }
    }

}
