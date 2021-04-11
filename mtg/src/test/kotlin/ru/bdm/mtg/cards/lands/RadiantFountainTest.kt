package ru.bdm.mtg.cards.lands

import org.junit.jupiter.api.Test
import ru.bdm.mtg.Battle
import ru.bdm.mtg.Place

internal class RadiantFountainTest {
    @Test
    fun test() {
        Battle().apply {
            val hp = me.hp
            me.add(Place.HAND, RadiantFountain())
            nextTurn()
            assert(me.hp == hp + 2)
        }
    }
}