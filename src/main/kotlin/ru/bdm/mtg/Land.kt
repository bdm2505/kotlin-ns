package ru.bdm.mtg

import ru.bdm.mtg.conditions.*



class Land(var color: Char = 'R', rotated: Boolean = false) : RotateCard(rotated) {

    fun conditionOne():Boolean {
        return inHand() && !me.isLandPlayable
    }
    fun reactionOne(){
        move(me.hand, me.lands)
    }

    fun conditionRotate():Boolean {
        return isActiveLand()
    }
    fun reactionRotate(){
        addMana(color)
        rotate()
    }


    override fun toString(): String {
        return super.toString() + "[$color]"
    }

    override fun copy(): Card {
        return Land(color, rotated)
    }
}