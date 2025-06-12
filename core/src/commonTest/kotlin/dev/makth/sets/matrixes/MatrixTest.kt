package dev.makth.sets.matrixes

import dev.makth.extensions.StringValue
import dev.makth.numbers.integers.IntegerFactory
import dev.makth.resolvables.variables.VariableFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MatrixTest {

    @Test
    fun rawString() {
        assertEquals(
            "(1, 2; 3, 4)",
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "\\begin{bmatrix} 1 & 2 \\\\ 3 & 4 \\end{bmatrix}",
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).laTeXString
        )
    }

    @Test
    fun rows() {
        assertEquals(
            listOf(
                listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
            ),
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).rows
        )
    }

    @Test
    fun columns() {
        assertEquals(
            listOf(
                listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(3)),
                listOf(IntegerFactory.instantiate(2), IntegerFactory.instantiate(4))
            ),
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).columns
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(VariableFactory.instantiate("x"), VariableFactory.instantiate("y")),
            MatrixFactory.instantiate(
                listOf(
                    listOf(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), VariableFactory.instantiate("y"))
                )
            ).variables
        )
    }

    @Test
    fun instantiateInvalidMatrix() {
        assertFailsWith(IllegalArgumentException::class) {
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3))
                )
            )
        }
    }

    @Test
    fun instantiateReal() {
        assertEquals(
            IntegerFactory.instantiate(1),
            MatrixFactory.instantiate(listOf(listOf(IntegerFactory.instantiate(1))))
        )
    }

    @Test
    fun transposition() {
        assertEquals(
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(3)),
                    listOf(IntegerFactory.instantiate(2), IntegerFactory.instantiate(4))
                )
            ),
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).transpose()
        )
    }

    @Test
    fun sumCorrectMatrix() {
        assertEquals(
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(6), IntegerFactory.instantiate(8)),
                    listOf(IntegerFactory.instantiate(10), IntegerFactory.instantiate(12))
                )
            ),
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).sum(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(5), IntegerFactory.instantiate(6)),
                        listOf(IntegerFactory.instantiate(7), IntegerFactory.instantiate(8))
                    )
                )
            )
        )
    }

    @Test
    fun sumIncorrectMatrix() {
        assertFailsWith(UnsupportedOperationException::class) {
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).sum(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(5), IntegerFactory.instantiate(6)),
                        listOf(IntegerFactory.instantiate(7), IntegerFactory.instantiate(8)),
                        listOf(IntegerFactory.instantiate(9), IntegerFactory.instantiate(10))
                    )
                )
            )
        }
    }

    @Test
    fun sumUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).sum(StringValue("test"))
        }
    }

    @Test
    fun multiplyCorrectMatrix() {
        assertEquals(
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(2340)),
                    listOf(IntegerFactory.instantiate(0), IntegerFactory.instantiate(1000))
                )
            ),
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(2), IntegerFactory.instantiate(3), IntegerFactory.instantiate(4)),
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(0), IntegerFactory.instantiate(0))
                )
            ).multiply(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(IntegerFactory.instantiate(0), IntegerFactory.instantiate(1000)),
                        listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(100)),
                        listOf(IntegerFactory.instantiate(0), IntegerFactory.instantiate(10))
                    )
                )
            )
        )
    }

    @Test
    fun multiplyIncorrectMatrix() {
        assertFailsWith(UnsupportedOperationException::class) {
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(2), IntegerFactory.instantiate(3), IntegerFactory.instantiate(4)),
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(0), IntegerFactory.instantiate(0))
                )
            ).multiply(
                MatrixFactory.instantiate(
                    listOf(
                        listOf(
                            IntegerFactory.instantiate(2),
                            IntegerFactory.instantiate(3),
                            IntegerFactory.instantiate(4)
                        ),
                        listOf(
                            IntegerFactory.instantiate(1),
                            IntegerFactory.instantiate(0),
                            IntegerFactory.instantiate(0)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun multiplyUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            MatrixFactory.instantiate(
                listOf(
                    listOf(IntegerFactory.instantiate(1), IntegerFactory.instantiate(2)),
                    listOf(IntegerFactory.instantiate(3), IntegerFactory.instantiate(4))
                )
            ).multiply(StringValue("test"))
        }
    }

}
