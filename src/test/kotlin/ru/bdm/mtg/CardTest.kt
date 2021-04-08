package ru.bdm.mtg

import org.junit.jupiter.api.Test

class CardTest {

    @Test
    fun testExecutors(){
        val exe = LandExecutor()
        val state = stateEmpty()
        val land = Land()

        state.me.lands += land

        val sc = state.clone()

        println(state)
        println(sc)
        println()
        (sc.me.lands.first() as Land).rotated = true
        println(state)
        println(sc)
        println()

        land.rotated = false

        val states = exe.resultStates(state, land)

        println(state)

        for(st in states)
            println(st)

    }
}