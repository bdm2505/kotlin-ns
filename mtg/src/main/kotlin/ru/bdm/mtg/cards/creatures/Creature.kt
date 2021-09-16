package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*

interface CreatureInterface : RotateCardInterface {





}

open class CreatureExecutor : RotateCardExecutor() {

    init {
        any(this::canAttack) {
            attacked()
        }
        any(this::canPlay) {
            play()
        }
        one(this::canEndAttack) {
            endAttacked()
        }

        any(this::canBlockAttack) {
            blockingReactions()
        }
    }

    fun creature(): Creature = card as Creature


    fun canAttack(): Boolean =
        isStartPhase() && inBattlefield() && !creature().rotated && !creature().attack && !creature().isWentOnBattlefield

    fun canEndAttack(): Boolean = isEndAttackPhase() && inBattlefield() && creature().attack


    fun canBlockAttack(): Boolean =
        isBlockPhase() && inBattlefield() && !creature().rotated && !creature().isBlocked && thereAreBlockableCreatures()

    fun thereAreBlockableCreatures(): Boolean {
        for (id in enemy().battlefield)
            if (canBlock(state(id)))
                return true
        return false
    }

    fun canBlock(card: Card): Boolean = card is Creature && card.attack && checkStatus(card)

    fun checkStatus(enemy: Creature): Boolean {
        return !enemy.isFlying() || creature().isFlying()
    }

//    fun blockingReactions(): List<() -> Unit> {
//        val list = mutableListOf<() -> Unit>()
//
//        for (index in enemy.battlefield) {
//            val cardEnemy = enemy(index)
//            if (canBlock(cardEnemy)) {
//                list.add {
//                    creature.isBlocked = true
//                    blockCreature(cardEnemy.id)
//                }
//            }
//        }
//
//        return list
//    }

//    fun attacked(): List<() -> Unit> = listOf {
//        creature.attack = true
//        rotate()
//    }

    fun blockCreature(enemyId: Int) {
        val enemyCreature = state(enemyId) as Creature
        val stateSwap = state.swap()
        enemyCreature.toDamage(stateSwap, creature)
        creature.toDamage(state, enemyCreature)
        if (creature.hp <= 0) {
            move(me.battlefield, me.graveyard)
            creature.endTurn(state)
            creature.removedFromBattlefield(state)
        }
        if (enemyCreature.hp <= 0) {
            move(this.enemy.battlefield, this.enemy.graveyard, enemyId)
            enemyCreature.endTurn(stateSwap)
            enemyCreature.removedFromBattlefield(stateSwap)
        }
    }

    fun play(): List<() -> Unit> = listOf {
        move()
        spendMana()
        creature.isWentOnBattlefield = true
    }

    fun endAttacked() {
        creature.toDamageInFace(state)
    }

    override fun endTurn(state: BattleState) {

        hp = maxHp
        attack = false
        isBlocked = false
        isWentOnBattlefield = false
    }
}

@Serializable
@SerialName("creature")
open class Creature() : RotateCard() {
    var force: Int = 0
    var hp: Int = 0
    var maxHp: Int = hp
    var attack: Boolean = false
    var isBlocked: Boolean = false
    var isWentOnBattlefield = false

    constructor(damage: Int, hp: Int) : this() {
        this.force = damage
        this.hp = hp
        this.maxHp = hp
    }


    init {
        tag(Tag.CREATURE)
    }

    open fun toDamage(state: BattleState, enemy: Creature) {
        enemy minusHp force
        println("creature to damage $enemy - $force $activeBuffs")
        for (buff in activeBuffs) {
            buff.attackCreature(state, this, enemy)
        }
    }

    open infix fun minusHp(damage: Int) {
        hp -= damage
    }

    open fun toDamageInFace(state: BattleState) {
        state.enemy.hp -= force
        for (buff in activeBuffs) {
            buff.attackFace(state, this, state.enemy)
        }
    }

    override fun executor(): Executor {
        return CreatureExecutor()
    }



    override fun toString(): String {
        return super.toString() + (if (attack) "A" else "") + (if (isBlocked) "B" else "") + "(${force}_$hp/$maxHp)"
    }

    override fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is Creature) return false
        if (!super.equals(card)) return false

        if (force != card.force) return false
        if (hp != card.hp) return false
        if (maxHp != card.maxHp) return false
        if (attack != card.attack) return false

        return true
    }


}