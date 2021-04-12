package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.cards.Creature

@Serializable
open class AbstractToken(val addForce: Int, val addHp: Int) : PassiveBuff() {

    override fun activate(card: AbstractCard) {
        card as Creature
        card.apply {
            force += addForce
            hp += addHp
            maxHp += addHp
        }
    }

    override fun deactivate(card: AbstractCard) {
        card as Creature
        card.apply {
            force -= addForce
            maxHp -= addHp
            if (hp > maxHp) hp = maxHp
        }
    }
}

@Serializable
object Token : AbstractToken(1, 1)