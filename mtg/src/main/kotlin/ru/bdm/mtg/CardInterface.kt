package ru.bdm.mtg


interface CardInterface {
    val state: BattleState
    val abstractCard: AbstractCard
    val card: Card
        get() = abstractCard as Card

    val me: StatePlayer
        get() = state.me
    val enemy: StatePlayer
        get() = state.enemy

    fun isStartPhase(player: StatePlayer = me): Boolean = player.phase == Phase.START
    fun isAttackPhase(player: StatePlayer = me): Boolean = player.phase == Phase.ATTACK
    fun isBlockPhase(player: StatePlayer = me): Boolean = player.phase == Phase.BLOCK
    fun isEndAttackPhase(player: StatePlayer = me): Boolean = player.phase == Phase.END_ATTACK
    fun isEndPhase(player: StatePlayer = me): Boolean = player.phase == Phase.END

    fun inHand(player: StatePlayer = me): Boolean = player.hand.contains(card.id)
    fun inLands(player: StatePlayer = me): Boolean = player.lands.contains(card.id)
    fun inBattlefield(player: StatePlayer = me): Boolean = player.battlefield.contains(card.id)

    fun canPlay() = inHand() && enoughMana() && (isStartPhase() || isEndPhase())

    fun enoughMana(player: StatePlayer = me): Boolean {
        for (mana in card.cost) {
            if (mana.key != Mana.NEUTRAL &&
                (!player.mana.containsKey(mana.key) || player.mana[mana.key]!! < mana.value)
            )
                return false
        }
        return card.cost.count() <= player.mana.count()
    }

    fun addMana(color: Mana) {
        me.mana.plus(color)
    }

    fun move(start: MutableSet<Int> = me.hand, end: MutableSet<Int> = me.battlefield, movedCard: Card = card) {
        start -= movedCard.id
        end += movedCard.id
    }

    fun move(start: MutableSet<Int>, end: MutableList<Int>, movedCard: Card = card) {
        if (start.contains(movedCard.id)) {
            start.remove(movedCard.id)
            end.add(movedCard.id)
        } else {
            System.err.println("error move $movedCard from $start to $end   $state")
        }
    }

    fun spendMana(player: StatePlayer = me) {

        for (m in card.cost) {
            player.mana[m.key]?.let {
                if (it > m.value)
                    player.mana[m.key] = it - m.value
                else
                    player.mana.remove(m.key)
            }
        }
        card.cost[Mana.NEUTRAL]?.let {
            var count = it
            for (m in player.mana) {
                if (m.value > count) {
                    player.mana[m.key] = m.value - count
                    break
                }
                if (m.value == count) {
                    player.mana.remove(m.key)
                    break
                }
                if (m.value < count) {
                    count -= m.value
                    player.mana.remove(m.key)
                }
            }
        }
    }

}

