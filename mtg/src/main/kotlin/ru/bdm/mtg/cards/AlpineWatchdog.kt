package ru.bdm.mtg.cards
interface VigilanceInterface : CreatureInterface {
  override fun attacked(){
    creature.attack = true
  }
}
class VigilanceExecutor : CreatureExecutor(), VigilanceInterface 

class AlpineWatchdog : Creature(2,2) {
  init{
    cost = "CW".toMana()
    tag(Tag.DOG)
  }
  override fun executor() = VigilanceExecutor()
}