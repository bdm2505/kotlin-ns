package ru.bdm.mtg

import org.junit.jupiter.api.Test
import ru.bdm.mtg.cards.CardSerializer
import ru.bdm.mtg.cards.Creature
import ru.bdm.mtg.cards.lands.Land
import ru.bdm.mtg.cards.lands.TwoColorLand

class TwoColorLandTest{

  @Test
  fun testPlay(){
    val land = TwoColorLand(Mana.RED, Mana.WHITE)
    Battle(ZeroPlayer("o"), ZeroPlayer("i")).apply{
      me.addIn(me.hand, land)
      nextTurn()
      assert(me.get(land).rotated)
      assert(me.get(land).place == Place.LANDS)
      me.get(land).reset()
      val states = executors.resultStates(state, listOf(land))
      assert(states.size == 2)
      assert(states[0].me.mana == "R".toCost())
      assert(states[0].me.mana == "W".toCost())
    }
  }

}