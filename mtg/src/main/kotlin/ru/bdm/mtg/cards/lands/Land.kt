package ru.bdm.mtg.cards.lands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*


open class LandExecutor : RotateCardExecutor() {

    fun canPlayLand(card: Card, state: BattleState): Boolean {
        card as Land
        return card.canPlay(state) && state.me.isLandPlayable
    }

    fun canRotateLand(card: Card, state: BattleState): Boolean {
        card as Land
        return state.me.inLands(card.id) && !card.rotated
    }

    open fun playLand(card: Card, state: BattleState) {
        state.move(card.id, state.me.hand, state.me.battlefield)
        state.me.isLandPlayable = true
    }


    override fun execute(action: Action, card: Card, state: BattleState) {
        card as Land
        when (action) {
            is PlayAction ->
                playLand(card, state)
            is RotateAction -> {
                card.addMana(state, card.color)
                card.rotate()
            }
        }
    }

    override fun actions(): List<Pair<(Card, BattleState) -> Boolean, Action>> {
        return listOf(
            ::canPlayLand to PlayAction,
            ::canRotateLand to RotateAction
        )
    }
}



@Serializable
@SerialName("Land")
open class Land() : RotateCard() {

    var color: Mana = Mana.RED

    constructor(color: Mana, rotate: Boolean = false) : this() {
        this.color = color
        this.rotated = rotate
    }


    override fun toString(): String {
        return super.toString() + "-$color"
    }

    override infix fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is Land) return false
        if (!super.equals(card)) return false

        if (color != card.color) return false

        return true
    }


}