package ru.bdm.mtg.cards

import ru.bdm.mtg.*

open class Creature(var force: Int, var hp: Int, var maxHp: Int = hp, var attack: Boolean = false ) : RotateCard() {

//
//    init {
//        one({ canPlay() }, {
//            spendMana()
//            move()
//        })
//
//        one({ canAttack() }, { attack = true })
//
//        one({ canEndAttack() }, { enemy.hp -= force })
//
//        gen({ canBlockAttack() && thereAreBlockableCreatures() }, { blockingReactions() } )
//    }
//
//    private fun blockingReactions(): Array<out () -> Unit> {
//        val list = mutableListOf<() -> Unit>()
//        for(card in enemy.battlefield)
//            if (canBlock(card))
//                list.add { blockCreature(card as Creature) }
//        return list.toTypedArray()
//    }
//
//
//    private fun thereAreBlockableCreatures(): Boolean {
//        for(card in enemy.battlefield)
//            if (canBlock(card))
//                return true
//        return false
//    }
//
//    private fun canBlock(card: Card): Boolean = card is Creature && card.attack && checkStatus(card)
//
//    private fun checkStatus(enemy: Creature): Boolean {
//        for(st in status)
//            if(st.canBlock(enemy.status))
//                return true
//        return false
//    }
//
//    fun blockCreature(creature: Creature){
//        creature.hp -= force
//        hp -= creature.force
//        if (hp < 0) {
//            move(me.battlefield, me.graveyard)
//            reset()
//        }
//        if(creature.hp < 0){
//            creature.move(enemy.battlefield, enemy.graveyard)
//            creature.reset()
//        }
//    }
//
//    private fun reset() {
//        hp = maxHp
//        attack = false
//    }
//
//    override fun copy(): Creature {
//        return super.copy() as Creature
//    }
//
//    fun conditionPlay(): Boolean {
//        return canPlay()
//    }
//
//    fun reactionPlay() {
//        play()
//    }
//
//    fun conditionAttack(): Boolean {
//        return !rotated && inBattlefield() && isAttackPhase()
//    }
//
//    fun reactionAttack() {
//        attack = true
//    }
//
//    fun conditionBlock(): Boolean {
//        return !rotated && inBattlefield() && isBlockPhase()
//    }
//
//    fun reactionBlock() {
//
//    }
}