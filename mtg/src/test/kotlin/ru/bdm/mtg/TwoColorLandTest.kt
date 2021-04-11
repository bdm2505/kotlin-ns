package ru.bdm.mtg

import org.junit.jupiter.api.Test
import ru.bdm.mtg.cards.lands.TwoColorLand

class TwoColorLandTest{

  @Test
  fun testPlay(){
    val land = TwoColorLand(Mana.RED, Mana.WHITE)
    Battle(ZeroPlayer("o"), ZeroPlayer("i")).apply{
      me.add(Place.HAND, land)
      nextTurn()
      assert(me.get(land).rotated)
      assert(me.lands.contains(land.id))
      me.get(land).endTurn(state)
      val states = CardExecutor.resultStates(state, listOf(land))
      assert(states.size == 2)
      assert(states[0].me.mana == "R".toMana())
      assert(states[1].me.mana == "W".toMana())
    }
  }

}