package dev.makth.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

class LongExtensionTest {

    @Test
    fun gcd() {
        assertEquals(2L, 4L.gcd(6L))
    }

    @Test
    fun gcdWithZero() {
        assertEquals(4L, 4L.gcd(0L))
    }

    @Test
    fun gcdWithZero2() {
        assertEquals(6L, 0L.gcd(6L))
    }

    @Test
    fun power() {
        assertEquals(8L, 2L.pow(3L))
    }

    @Test
    fun nthRoot() {
        assertEquals(2.0, 4L.nthRoot(2L), 0.0)
    }

    @Test
    fun nthRoot2() {
        assertEquals(16.0, 256L.nthRoot(2L), 0.0)
    }

}
