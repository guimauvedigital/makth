package dev.makth.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BooleanValueTest {

    @Test
    fun toAlgorithmStringTrue() {
        assertEquals("true", BooleanValue(true).algorithmString)
    }

    @Test
    fun toRawStringTrue() {
        assertEquals("true", BooleanValue(true).rawString)
    }

    @Test
    fun toLaTeXStringTrue() {
        assertEquals("\\text{true}", BooleanValue(true).laTeXString)
    }

    @Test
    fun toAlgorithmStringFalse() {
        assertEquals("false", BooleanValue(false).algorithmString)
    }

    @Test
    fun toRawStringFalse() {
        assertEquals("false", BooleanValue(false).rawString)
    }

    @Test
    fun toLaTeXStringFalse() {
        assertEquals("\\text{false}", BooleanValue(false).laTeXString)
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(true, BooleanValue(true).equals(BooleanValue(true)))
    }

    @Test
    fun testEqualsFalse() {
        assertEquals(false, BooleanValue(true).equals(BooleanValue(false)))
    }

    @Test
    fun testEqualsUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            BooleanValue(true).equals(StringValue("Hello world!"))
        }
    }

}
