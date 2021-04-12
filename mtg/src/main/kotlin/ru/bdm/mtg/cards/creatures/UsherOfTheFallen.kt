package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*
import ru.bdm.mtg.cards.chips.WhiteHumanWarrior

object UsherOfTheFallenCost {
    val cost = "CW".toMana()
}

interface UsherOfTheFallenInterface : CreatureInterface {
    val usher: UsherOfTheFallen
        get() = abstractCard as UsherOfTheFallen

    fun canBoast() =
        inBattlefield() && usher.attack && isEndPhase() && enoughMana(UsherOfTheFallenCost.cost) && !usher.isBoast

    fun boast() {
        usher.isBoast = true
        spendMana(UsherOfTheFallenCost.cost)
        me.add(Place.BATTLEFIELD, WhiteHumanWarrior())
    }
}

class UsherOfTheFallenExecutor : CreatureExecutor(), UsherOfTheFallenInterface {
    init {
        one(this::canBoast) {
            boast()
        }
    }
}

@Serializable
@SerialName("UsherOfTheFallen")
class UsherOfTheFallen(var isBoast: Boolean = false) : Creature(2, 1) {
    init {
        cost = "W".toMana()
        tag(Tag.SPIRIT, Tag.WARRIOR)
    }

    override fun executor(): Executor = UsherOfTheFallenExecutor()

    override fun endTurn(state: BattleState) {
        super.endTurn(state)
        isBoast = false
    }
}