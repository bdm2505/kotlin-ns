package ru.bdm.mtg.cards

import org.junit.jupiter.api.Test
import ru.bdm.mtg.Battle
import ru.bdm.mtg.Place

internal class ArchfiendsVesselTest {

    @Test
    fun test() {
        Battle().apply {
            val arch = ArchfiendsVessel()
            me.add(Place.GRAVEYARD, arch)
            nextTurn()
            me.graveyard -= arch.id
            me.battlefield += arch.id
            nextTurn()
            println(state)
            me.apply {
                assert(me.battlefield.size == 1)
                assert(me(me.battlefield.first()) is BlackDaemon)
            }
        }
    }
}