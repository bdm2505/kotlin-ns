package ru.bdm.mtg

import ru.bdm.mtg.cards.Creature




interface CardInterface {
    val state: State
    val card: Card

    val me: StatePlayer
        get() = state.me
    val enemy: StatePlayer
        get() = state.enemy

    fun isStartPhase(player: StatePlayer = me): Boolean = player.phase == Phase.START
    fun isAttackPhase(player: StatePlayer = me): Boolean = player.phase == Phase.ATTACK
    fun isBlockPhase(player: StatePlayer = me): Boolean = player.phase == Phase.BLOCK
    fun isEndAttackPhase(player: StatePlayer = me): Boolean = player.phase == Phase.END_ATTACK
    fun isEndPhase(player: StatePlayer = me): Boolean = player.phase == Phase.END

    fun inHand(player: StatePlayer = me): Boolean = player.hand.contains(card)
    fun inLands(player: StatePlayer = me): Boolean = player.lands.contains(card)
    fun inBattlefield(player: StatePlayer = me): Boolean = player.battlefield.contains(card)

    fun enoughMana(player: StatePlayer = me): Boolean {
        for (mana in card.cost) {
            if (mana.key != Mana.NEUTRAL &&
                (!player.mana.containsKey(mana.key) || player.mana[mana.key]!! < mana.value)
            )
                return false
        }
        return card.cost.count() <= player.mana.count()
    }

    fun addMana(color: Mana){
        me.mana.plus(color)
    }

    fun move(start: MutableSet<Card> = me.hand, end : MutableSet<Card> = me.battlefield)  {
        start -= card
        end += card
    }
    fun move(start: MutableSet<Card>, end : ArrayDeque<Card>)  {
        start -= card
        end.addFirst(card)
    }

    fun spendMana( player: StatePlayer = me){

        for(m in card.cost){
            player.mana[m.key]?.let {
                if (it > m.value)
                    player.mana[m.key] = it - m.value
                else
                    player.mana.remove(m.key)
            }
        }
        card.cost[Mana.NEUTRAL]?.let {
            var count = it
            for (m in player.mana){
                if(m.value > count){
                    player.mana[m.key] = m.value - count
                    break
                }
                if (m.value == count){
                    player.mana.remove(m.key)
                    break
                }
                if (m.value < count){
                    count -= m.value
                    player.mana.remove(m.key)
                }
            }
        }
    }
    fun canPlay() = inHand() && enoughMana() && (isStartPhase() || isEndPhase())

}



//
//
//
//fun CardExecutor.canAttack(card: Creature): Boolean = isAttackPhase() && inBattlefield(card) && !card.rotated
//fun CardExecutor.canEndAttack(card: Creature): Boolean = isEndAttackPhase() && inBattlefield(card) && card.attack
//fun CardExecutor.canBlockAttack(card: Creature): Boolean = isBlockPhase() && inBattlefield(card) && !card.rotated