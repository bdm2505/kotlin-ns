package ru.bdm.mtg

open class Action(val message: String) {

    open fun log(card: Card, state: BattleState): String {
        return card.name + " " + message
    }

    override fun toString(): String {
        return message
    }
}

object PlayAction: Action("play")

object AttackAction: Action("attack")

object RotateAction: Action("rotate")

class BlockAction(val id: Int): Action("block ") {
    override fun log(card: Card, state: BattleState): String {
        return super.log(card, state) + state(id).name
    }
}

class ChoiceColorAction(val color: Mana): Action("rotate and choice color "){
    override fun log(card: Card, state: BattleState): String {
        return super.log(card, state) + color
    }
}

