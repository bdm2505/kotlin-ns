package ru.bdm.mtg

import ru.bdm.mtg.conditions.*

class Land(var color: Char = 'R', rotated: Boolean = false) : RotateCard(rotated) {
    init {
        act({
            inHand() && !me.isLandPlayable
        }, {
            move(me.hand, me.lands)
        })
        act({
            isActiveLand()
        },{
            rotate()
            addMana(color)
        })
    }

    override fun toString(): String {
        return super.toString() + "[$color]"
    }

    override fun copy(): Card {
        return Land(color, rotated)
    }
}