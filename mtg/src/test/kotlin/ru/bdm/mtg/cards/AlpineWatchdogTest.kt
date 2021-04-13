package ru.bdm.mtg.cards

import org.junit.jupiter.api.Test
import ru.bdm.mtg.Battle
import ru.bdm.mtg.Place
import ru.bdm.mtg.cards.creatures.AlpineWatchdog
import ru.bdm.mtg.turnToEnd

class AlpineWatchdogTest{

  @Test 
  fun test(){
    Battle().apply{
      val alp = AlpineWatchdog()
      me.add(Place.BATTLEFIELD, alp)
      val hp = enemy.hp
      nextTurn()



      assert(me.get(alp).attack)
      assert(!me.get(alp).rotated)

      turnToEnd()
      assert(enemy.hp == hp - alp.force)
    }
  }
}