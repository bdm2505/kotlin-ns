package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.*


open class TwoColorLandExecutor : LandExecutor() {

    open fun playLandAndRotate() {
        move(me().hand, me().lands)
        me().isLandPlayable = true
        rotate()
    }

    override fun actions(): List<Pair<() -> Boolean, Action>> {
        val land = card as TwoColorLand
        return listOf(
            ::canPlayLand to PlayAction,
            ::canRotateLand to ChoiceColorAction(land.color),
            ::canRotateLand to ChoiceColorAction(land.colorTwo),
        )
    }

    override fun execute(action: Action) = when (action) {
        is PlayAction -> playLandAndRotate()
        is ChoiceColorAction -> {
            rotate()
            addMana(action.color)
        }
        else -> throw Exception("no correct action $action for card $card")
    }

}

@Serializable
open class TwoColorLand : Land {
    var colorTwo: Mana = Mana.NEUTRAL

    constructor() : super()
    constructor(color: Mana, colorTwo: Mana) : super(color) {
        this.colorTwo = colorTwo
    }

    override fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is TwoColorLand) return false
        if (!super.equals(card)) return false

        if (colorTwo != card.colorTwo) return false

        return true
    }

    override fun toString(): String {
        return super.toString() + " or $colorTwo'"
    }
}