package ru.bdm.mtg.conditions

import ru.bdm.mtg.Card
import ru.bdm.mtg.Land
import ru.bdm.mtg.Mana
import ru.bdm.mtg.StatePlayer

fun Card.inHand(player: StatePlayer = me): Boolean {
    return player.hand.containsKey(this)
}
fun Card.inBattlefield(player: StatePlayer = me):Boolean = player.battlefield.containsKey(this)

fun Land.isActiveLand(): Boolean {
    return me.lands.containsKey(this) && !rotated
}

fun Card.enoughMana(player: StatePlayer = me): Boolean {
    for(mana in cost){
        if (mana.key != Mana.NEUTRAL &&
            (!player.mana.containsKey(mana.key) || player.mana[mana.key]!! < mana.value ))
            return false
    }
    return cost.count() <= player.mana.count()
}

fun Card.canPlay() = inHand() && enoughMana()