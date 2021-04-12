package ru.bdm.mtg.cards.creatures

import ru.bdm.mtg.Tag
import ru.bdm.mtg.toMana

interface VigilanceInterface : CreatureInterface {
  override fun attacked() = listOf {
    creature.attack = true
  }
}

class VigilanceExecutor : CreatureExecutor(), VigilanceInterface

class AlpineWatchdog : Creature(2, 2) {
  init {
    cost = "CW".toMana()
    tag(Tag.DOG)
  }
  override fun executor() = VigilanceExecutor()
}