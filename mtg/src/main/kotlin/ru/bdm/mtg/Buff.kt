package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.cards.creatures.Creature

@Serializable
open class Buff {

  override fun toString(): String {
    return this::class.simpleName!!
  }
}

@Serializable
open class PassiveBuff : Buff() {


}

@Serializable
open class ActiveBuff : PassiveBuff() {
}

@Serializable
object LifeLink : ActiveBuff() {
  fun attackCreature(state: BattleState, me: Creature, enemy: Creature) {
    state.me.hp += me.force
    println("chain of life attack $state")
  }

   fun attackFace(state: BattleState, me: Creature, enemy: StatePlayer) {
    state.me.hp += me.force
  }
}

@Serializable
object DeathTouch : ActiveBuff() {
   fun attackCreature(state: BattleState, me: Creature, enemy: Creature) {
    enemy.hp = 0
  }
}
