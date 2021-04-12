package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.cards.creatures.Creature

@Serializable
abstract class Buff {

  override fun toString(): String {
    return this::class.simpleName!!
  }
}

@Serializable
open class PassiveBuff : Buff() {

  open fun activate(card: AbstractCard) {}

  open fun deactivate(card: AbstractCard) {}

}

@Serializable
open class ActiveBuff : PassiveBuff() {
  open fun attackCreature(state: BattleState, me: Creature, enemy: Creature) {}

  open fun attackFace(state: BattleState, me: Creature, enemy: StatePlayer) {}

  open fun endAction(state: BattleState, card: AbstractCard) {}
}

@Serializable
object LifeLink : ActiveBuff() {
  override fun attackCreature(state: BattleState, me: Creature, enemy: Creature) {
    state.me.hp += me.force
    println("chain of life attack $state")
  }

  override fun attackFace(state: BattleState, me: Creature, enemy: StatePlayer) {
    state.me.hp += me.force
  }
}

@Serializable
object DeathTouch : ActiveBuff() {
  override fun attackCreature(state: BattleState, me: Creature, enemy: Creature) {
    enemy.hp = 0
  }
}
