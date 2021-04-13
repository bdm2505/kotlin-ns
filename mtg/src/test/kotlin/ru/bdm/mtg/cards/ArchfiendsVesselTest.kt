package ru.bdm.mtg.cards

import org.junit.jupiter.api.Test
import ru.bdm.mtg.Battle
import ru.bdm.mtg.Place
import ru.bdm.mtg.cards.chips.BlackDaemon
import ru.bdm.mtg.cards.creatures.ArchfiendsVessel
import ru.bdm.mtg.cards.creatures.Creature
import ru.bdm.mtg.turnToEnd

internal class ArchfiendsVesselTest {

    @Test
    fun testCreateBlackDaemon() {
        Battle().apply {
            val arch = ArchfiendsVessel()
            me.add(Place.GRAVEYARD, arch)
            nextTurn()
            nextTurn()
            nextTurn()
            me.graveyard -= arch.id
            me.battlefield += arch.id
            nextTurn()
            state = state.swap()


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
          enemy.add(Place.BATTLEFIELD, Creature(3, 3))
          val hp = me.hp
          println("hp = $hp")
          turnToEnd()
          assert(me.hp == (hp + 1))
          assert(me.graveyard.size == 1)
        
      }
    }
}