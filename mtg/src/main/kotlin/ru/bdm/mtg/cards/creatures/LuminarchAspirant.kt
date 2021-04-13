package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Executor
import ru.bdm.mtg.Tag
import ru.bdm.mtg.Token

interface LuminarchAspirantInterface : CreatureInterface {
    fun canActivateSpell() = inBattlefield() && !creature.isWentOnBattlefield

    fun getActions() = me.battlefield.map {
        {
            me<Creature>(it).addPassiveBuff(Token)
        }
    }
}

class LuminarchAspirantExecutor : CreatureExecutor(), LuminarchAspirantInterface {

    init {
        any(this::canActivateSpell) {
            getActions()
        }
    }
}

@Serializable
@SerialName("LuminarchAspirant")
class LuminarchAspirant() : Creature(1, 1) {

    init {
        tag(Tag.HUMAN, Tag.CLERIC)
    }

    override fun executor(): Executor = LuminarchAspirantExecutor()
}