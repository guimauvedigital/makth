package dev.makth.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StringValueTest {

    @Test
    fun algorithmString() {
        assertEquals("\"Hello world!\"", StringValue("Hello world!").algorithmString)
    }

    @Test
    fun rawString() {
        assertEquals("Hello world!", StringValue("Hello world!").rawString)
    }

    @Test
    fun laTeXString() {
        assertEquals("\\text{Hello world!}", StringValue("Hello world!").laTeXString)
    }

    @Test
    fun toAlgorithmStringWithLaTeX() {
        assertEquals("\$x ^ 2\$", StringValue("x ^ 2", true).algorithmString)
    }

    @Test
    fun toRawStringWithLaTeX() {
        assertEquals("x ^ 2", StringValue("x ^ 2", true).rawString)
    }

    @Test
    fun toLaTeXStringWithLaTeX() {
        assertEquals("x ^ 2", StringValue("x ^ 2", true).laTeXString)
    }

    @Test
    fun toAlgorithmStringWithEscape() {
        assertEquals("\"Hello \\\"world\\\"!\"", StringValue("Hello \"world\"!").algorithmString)
    }

    @Test
    fun toRawStringWithEscape() {
        assertEquals("Hello \"world\"!", StringValue("Hello \"world\"!").rawString)
    }

    @Test
    fun toLaTeXStringWithEscape() {
        assertEquals("\\text{Hello \"world\"!}", StringValue("Hello \"world\"!").laTeXString)
    }

    @Test
    fun toAlgorithmStringWithLaTeXAndEscape() {
        assertEquals("\$x \\\$ 2\$", StringValue("x \$ 2", true).algorithmString)
    }

    @Test
    fun toRawStringWithLaTeXAndEscape() {
        assertEquals("x \$ 2", StringValue("x \$ 2", true).rawString)
    }

    @Test
    fun toLaTeXStringWithLaTeXAndEscape() {
        assertEquals("x \\\$ 2", StringValue("x \$ 2", true).laTeXString)
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(true, StringValue("Hello world!").equals(StringValue("Hello world!")))
    }

    @Test
    fun testEqualsFalse() {
        assertEquals(false, StringValue("Hello world!").equals(StringValue("Hello world")))
    }

    @Test
    fun testEqualsUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            StringValue("Hello world!").equals(BooleanValue(true))
        }
    }

}
