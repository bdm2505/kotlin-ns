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

    fun isStartPhase(): Boolean = state.phase == Phase.START
    fun isBlockPhase(): Boolean = state.phase == Phase.BLOCK
    fun isEndAttackPhase(): Boolean = state.phase == Phase.END_ATTACK
    fun isEndPhase(): Boolean = state.phase == Phase.END
    fun isActivePhase(): Boolean = isStartPhase() || isEndPhase()

    fun inHand(player: StatePlayer = me): Boolean = player.hand.contains(card.id)
    fun inLands(player: StatePlayer = me): Boolean = player.lands.contains(card.id)
    fun inBattlefield(player: StatePlayer = me): Boolean = player.battlefield.contains(card.id)
    fun inExile(player: StatePlayer = me): Boolean = player.exile.contains(card.id)
    fun inGraveyard(player: StatePlayer = me): Boolean = player.graveyard.contains(card.id)

    fun canPlay() = inHand() && enoughMana() && (isStartPhase() || isEndPhase())

    fun enoughMana(cost: Kit<Mana> = card.cost, player: StatePlayer = me): Boolean {
        for (mana in cost) {
            if (
                mana.key != Mana.NEUTRAL &&
                (!player.mana.containsKey(mana.key) || player.mana[mana.key]!! < mana.value)
            )
                return false
        }
        return cost.count() <= player.mana.count()
    }

    fun addMana(color: Mana) {
        me.mana.add(color)
    }

    fun move(
        start: MutableCollection<Int> = me.hand,
        end: MutableCollection<Int> = me.battlefield,
        movedCard: Int = card.id
    ) {
        start -= movedCard
        end += movedCard
    }


    fun spendMana(cost: Kit<Mana> = card.cost, player: StatePlayer = me) {
        println("spend mana ${me.mana} - $cost ")
        for (m in cost) {
            player.mana[m.key]?.let {
                if (it > m.value)
                    player.mana[m.key] = it - m.value
                else
                    player.mana.remove(m.key)
            }
        }
        cost[Mana.NEUTRAL]?.let {
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
        println("end spend mana ${me.mana}")
    }


}

