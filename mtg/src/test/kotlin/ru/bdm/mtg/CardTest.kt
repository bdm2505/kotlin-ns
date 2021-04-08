package ru.bdm.mtg

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.junit.jupiter.api.Test
import ru.bdm.mtg.cards.CardSerializer
import ru.bdm.mtg.cards.Creature

class CardTest {

    @Test
    fun testExecutors() {
        val exe = LandExecutor()
        val state = State()
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

        for (st in states)
            println(st)

    }

    @Serializable
    open class A {
        var s: String = "sa"
    }

    @Serializable
    class B : A() {
        var sq = "qw"
    }


    @Test
    fun toJson() {
        val state = State()
        state.me.hp = 90
        state.me.hand += Creature(2,4)
        state.me.battlefield += Land()

        val str = CardSerializer.encode(state)

        val res = CardSerializer.decode(str)

        assert(res.toString() == state.toString())
    }
}