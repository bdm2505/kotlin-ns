package ru.bdm.mtg

import org.junit.jupiter.api.Test

class KitTest {

    @Test
    fun testToMutable(){
        val a = mutableSetOf<Int>(1,2)
        val b = a.toMutableSet()
        assert(a !== b)
        assert(a == b)
    }

}