package ru.bdm.mtg.cards

import org.junit.jupiter.api.Test

class AlpineWatchdogTest{

  @Test 
  fun test(){
    Battle().apply{
      val alp = AlpineWatchdog()
      me.add(Place.BATTLEFIELD, alp)
      nextTurn()
      nextTurn()
      
      assert(me.get(alp).attack)
      assert(!me.get(alp).rotate)
      val hp = enemy.hp
      
      nextTurn()
      nextTurn()
      assert(enemy.hp == hp - alp.force)
    }
  }
}