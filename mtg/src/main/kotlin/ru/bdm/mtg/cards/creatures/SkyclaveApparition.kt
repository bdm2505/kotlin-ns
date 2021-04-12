package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*
import ru.bdm.mtg.cards.chips.BlueIllusion

interface SkyclaveApparitionInterface : CreatureInterface {
    val sky: SkyclaveApparition
        get() = abstractCard as SkyclaveApparition

    fun findEnemies() = enemy.battlefield.filter { enemy<Creature>(it).cost.counts() <= 4 }

    override fun play(): List<() -> Unit> {
        val result = findEnemies().map {
            {
                super.play().forEach { it() }
                sky.enemyManaCost = enemy<Creature>(it).cost.counts()
                enemy.battlefield -= it
            }
        }
        return if (result.isEmpty()) listOf { super.play().forEach { it() } } else result
    }
}

class SkyclaveApparitionExecutor : CreatureExecutor(), SkyclaveApparitionInterface {

}

@Serializable
@SerialName("SkyclaveApparition")
class SkyclaveApparition(var enemyManaCost: Int = 0) : Creature(2, 2) {

    init {
        cost = "CWW".toMana()
        tag(Tag.KOR, Tag.SPIRIT)
    }

    override fun executor(): Executor = SkyclaveApparitionExecutor()

    override fun removedFromBattlefield(state: BattleState) {
        super.removedFromBattlefield(state)
        state.enemy.add(Place.BATTLEFIELD, BlueIllusion(enemyManaCost, enemyManaCost))
    }

}