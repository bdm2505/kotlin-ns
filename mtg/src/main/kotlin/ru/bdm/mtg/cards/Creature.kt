package ru.bdm.mtg.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*

interface CreatureInterface : RotateCardInterface{
    val creature: Creature
        get() = card as Creature

    fun canAttack(): Boolean = isAttackPhase() && inBattlefield() && !creature.rotated && !creature.attack

    fun canEndAttack(): Boolean = isEndAttackPhase() && inBattlefield() && creature.attack
    fun canBlockAttack(): Boolean = isBlockPhase() && inBattlefield() && !creature.rotated

    fun thereAreBlockableCreatures(): Boolean {
        for(card in enemy.battlefield)
            if (canBlock(card))
                return true
        return false
    }

    fun canBlock(card: AbstractCard): Boolean = card is Creature && card.attack && checkStatus(card)

    fun checkStatus(enemy: Creature): Boolean {
        for(st in creature.status)
            if(st.canBlock(enemy.status))
                return true
        return false
    }

    fun blockingReactions(): List<() -> Unit> {
        val list = mutableListOf<() -> Unit>()
        for(card in enemy.battlefield)
            if (canBlock(card))
                list.add { blockCreature(card as Creature) }
        return list
    }

    fun blockCreature(enemy: Creature){
        enemy.hp -= creature.force
        creature.hp -= enemy.force
        if (creature.hp < 0) {
            move(me.battlefield, me.graveyard)
            creature.reset()
        }
        if(enemy.hp < 0){
            move(this.enemy.battlefield, this.enemy.graveyard, enemy)
            enemy.reset()
        }
    }
}
class CreatureExecutor : Executor(), CreatureInterface {

    fun conditionCanAttack():Boolean {
        return canAttack()
    }
    fun reactionCanAttack(): List<() -> Unit>{
        return listOf {
            creature.attack = true
            rotate()
        }
    }

    fun conditionCanPlay():Boolean {
        return canPlay()
    }
    fun reactionCanPlay(): List<() -> Unit>{
        return listOf { play() }
    }
    fun conditionEndAttack():Boolean {
        return canEndAttack()
    }
    fun reactionEndAttack(): List<() -> Unit>{
        return listOf { enemy.hp -= creature.force }
    }
    fun conditionBlockAttack():Boolean {
        return canBlockAttack() && thereAreBlockableCreatures()
    }
    fun reactionBlockAttack(): List<() -> Unit>{
        return blockingReactions()
    }


}

@Serializable
@SerialName("creature")
open class Creature() : RotateCard() {
    var force: Int = 0
    var hp: Int = 0
    var maxHp: Int = hp
    var attack: Boolean = false

    constructor(f: Int, hp: Int) : this() {
        this.force = f
        this.hp = hp
        this.maxHp = hp
    }
    override fun reset() {
        super.reset()
        hp = maxHp
        attack = false
    }

    override fun toString(): String {
        return super.toString() + (if(attack) "A" else "") + "(${force}_$hp/$maxHp)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Creature) return false
        if (!super.equals(other)) return false

        if (force != other.force) return false
        if (hp != other.hp) return false
        if (maxHp != other.maxHp) return false
        if (attack != other.attack) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + force
        result = 31 * result + hp
        result = 31 * result + maxHp
        result = 31 * result + attack.hashCode()
        return result
    }


}