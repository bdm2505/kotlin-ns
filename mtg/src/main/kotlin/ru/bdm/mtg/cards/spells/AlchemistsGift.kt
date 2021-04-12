package ru.bdm.mtg.cards.spells

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*
import ru.bdm.mtg.cards.creatures.Creature

interface AlchemistsGiftInterface : InstantInterface {

    override fun getActions(): List<() -> Unit> = meCreatures().flatMap {
        listOf({
            play()
            val cr = me<Creature>(it)
            cr.addPassiveBuff(Token)
            cr.addActiveBuff(DeathTouch)
        }, {
            play()
            val cr = me<Creature>(it)
            cr.addPassiveBuff(Token)
            cr.addActiveBuff(LifeLink)
        })
    }

}

class AlchemistsGiftExecutor : InstantExecutor(), AlchemistsGiftInterface

@Serializable
@SerialName("AlchemistsGift")
class AlchemistsGift() : Instant() {

    init {
        cost = "B".toMana()
    }

    override fun executor(): Executor = AlchemistsGiftExecutor()
}