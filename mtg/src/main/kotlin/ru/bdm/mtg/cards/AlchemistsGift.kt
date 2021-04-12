package ru.bdm.mtg.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*

interface AlchemistsGiftInterface : InstantInterface {

    override fun getActions(): List<() -> Unit> = meCreatures().flatMap {
        listOf({
            val cr = me<Creature>(it)
            cr.addPassiveBuff(Token)
            cr.addActiveBuff(DeathTouch)
        }, {
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