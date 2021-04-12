package ru.bdm.mtg.cards

import org.junit.jupiter.api.Test
import ru.bdm.mtg.Battle
import ru.bdm.mtg.Place

internal class ArchfiendsVesselTest {

    @Test
    fun testCreateBlackDaemon() {
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
    @Test
    fun testAddLife(){
      Battle().apply {
        val arch = ArchfiendsVessel()
        me.add(Place.BATTLEFIELD, arch)
        enemy.add(Place.BATTLEFIELD, Creature(3,3))
        val hp = me.hp
        nextTurn()
        nextTurn()
        nextTurn()
        nextTurn()
        assert(me.hp == hp + 1)
        assert(me.graveyard.size() == 1)
        
      }
    }
}