package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.*
import ru.bdm.mtg.cards.chips.BlackDaemon


@Serializable
@SerialName("ArchfiendsVessel")
class ArchfiendsVessel : Creature(1, 1) {
    init {
        cost = "B".toMana()
        tag(Tag.CLERIC, Tag.HUMAN, Tag.CREATURE)
        addActiveBuff(LifeLink)
    }

    var wasInTheGraveyard: Boolean = false
    override fun endAction(state: BattleState) {
        super.endAction(state)
        println("end Action $state")
        if (state.me.graveyard.contains(id))
            wasInTheGraveyard = true

        if (wasInTheGraveyard && state.me.battlefield.contains(id)) {
            state.me.apply {
                battlefield -= id
                add(Place.BATTLEFIELD, BlackDaemon().apply { isWentOnBattlefield = true })
            }
        }
    }

    override fun executor() = CreatureExecutor()
}