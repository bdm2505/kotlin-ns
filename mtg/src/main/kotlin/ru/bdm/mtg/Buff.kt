package ru.bdm.mtg

import kotlinx.serialization.Serializable

@Serializable
abstract class Buff{
 
}

class PassiveBuff : Buff(){
  
  open fun activate(card: AbstractCard) {}
  
  open fun deactivate(card : AbstractCard) {}
  
}

class ActiveBuff : PassiveBuff(){
  open fun attackCreature(state : BattleState, me: Creature, enemy : Creature){}
  
  open fun attackFace(state: BattleState, me: Creature, enemy: StatePlayer){}
  
  open fun endAction(state BattleState, card: AbstractCard){}
}

object ChainOfLife : ActiveBuff(){
  override fun attackCreature(state : BattleState, me: Creature, enemy : Creature){
    state.me.hp += me.force
  }
  override fun attackFace(state: BattleState, me: Creature, enemy: StatePlayer){
    state.me.hp += me.force
  }
  
}

class AbstractToken(val addForce : Int, val addHp : Int) : PassiveBuff(){
  
  override fun activate(card : AbstractCard){
    card as Creature
    card.apply{
      force += addForce
      hp += addHp
      maxHp += addHp
    }
  }
  
  override fun deactivate(card : AbstractCard){
    card as Creature
    card.apply{
      force -= addForce
      maxHp -= addHp
      if(hp > maxHp) hp = maxHp
    }
  }
}

object Token : AbstractToken(1, 1)