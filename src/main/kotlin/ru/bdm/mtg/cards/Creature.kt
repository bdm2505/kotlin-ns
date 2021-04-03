package ru.bdm.mtg.cards

import ru.bdm.mtg.Card
import ru.bdm.mtg.RotateCard
import ru.bdm.mtg.conditions.canPlay
import ru.bdm.mtg.conditions.inBattlefield
import ru.bdm.mtg.play

open class Creature(var hp:Int, var maxHp: Int, var attack: Boolean = false) : RotateCard() {


    override fun copy(): Creature {
        return super.copy() as Creature
    }

    fun conditionPlay(): Boolean {
        return canPlay()
    }

    fun reactionPlay() {
        play()
    }

    fun conditionAttack():Boolean {
        return !rotated && inBattlefield()
    }
    fun reactionAttack(){
        attack = true
    }
}