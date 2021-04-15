package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Tag
import ru.bdm.mtg.toMana

interface VigilanceInterface : CreatureInterface {
  override fun attacked() = listOf {
    creature.attack = true
  }
}

class VigilanceExecutor : CreatureExecutor(), VigilanceInterface

@Serializable
@SerialName("AlpineWatchdog")
class AlpineWatchdog : Creature(2, 2) {
    init {
        cost = "CW".toMana()
        tag(Tag.DOG)
    }

    override fun executor() = VigilanceExecutor()
}