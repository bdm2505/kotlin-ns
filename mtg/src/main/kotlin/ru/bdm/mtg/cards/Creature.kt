package ru.bdm.mtg.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.AbstractCard
import ru.bdm.mtg.Executor
import ru.bdm.mtg.RotateCard
import ru.bdm.mtg.RotateCardInterface

interface CreatureInterface : RotateCardInterface {
    val creature: Creature
        get() = card as Creature

    fun canAttack(): Boolean =
        isAttackPhase() && inBattlefield() && !creature.rotated && !creature.attack && !creature.isWentOnBattlefield

    fun canEndAttack(): Boolean = isEndAttackPhase() && inBattlefield() && creature.attack


    fun canBlockAttack(): Boolean =
        isBlockPhase() && inBattlefield() && !creature.rotated && !creature.isBlocked && thereAreBlockableCreatures()

    fun thereAreBlockableCreatures(): Boolean {
        for (index in enemy.battlefield)
            if (canBlock(enemy(index)))
                return true
        return false
    }

    fun canBlock(card: AbstractCard): Boolean = card is Creature && card.attack && checkStatus(card)

    fun checkStatus(enemy: Creature): Boolean {
        for (st in creature.status)
            if (st.canBlock(enemy.status))
                return true
        return false
    }

    fun blockingReactions(): List<() -> Unit> {
        val list = mutableListOf<() -> Unit>()

        for (index in enemy.battlefield) {
            val cardEnemy = enemy(index)
            if (canBlock(cardEnemy)) {
                list.add {
                    creature.isBlocked = true
                    blockCreature(cardEnemy.id)
                }
            }
        }

        return list
    }

    fun blockCreature(id: Int) {
        val enemyCreature = enemy<Creature>(id)
        enemyCreature.hp -= creature.force
        creature.hp -= enemyCreature.force
        if (creature.hp <= 0) {
            move(me.battlefield, me.graveyard)
            creature.reset()
        }
        if (enemyCreature.hp <= 0) {
            move(this.enemy.battlefield, this.enemy.graveyard, enemyCreature)
            enemyCreature.reset()
        }
    }
}

class CreatureExecutor : Executor(), CreatureInterface {

    init {
        one(this::canAttack) {
            creature.attack = true
            rotate()
        }
        one(this::canPlay) {
            play()
            creature.isWentOnBattlefield = true
        }
        one(this::canEndAttack) {
            enemy.hp -= creature.force
        }

        any(this::canBlockAttack) {
            blockingReactions()
        }
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

    constructor(f: Int, hp: Int) : this() {
        this.force = f
        this.hp = hp
        this.maxHp = hp
    }

    override fun executor(): Executor {
        return CreatureExecutor()
    }

    override fun reset() {
        super.reset()
        hp = maxHp
        attack = false
        isBlocked = false
        isWentOnBattlefield = false
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