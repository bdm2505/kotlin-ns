package ru.bdm.mtg.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.BattleState
import ru.bdm.mtg.Place
import ru.bdm.mtg.Tag
import ru.bdm.mtg.toMana


@Serializable
@SerialName("ArchfiendsVessel")
class ArchfiendsVessel : Creature(1, 1) {
    init {
        cost = "B".toMana()
        tag(Tag.CLERIC, Tag.HUMAN, Tag.CREATURE)
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

    override fun toDamage(state: BattleState, enemy: Creature) {
        super.toDamage(state, enemy)
        state.me.hp += force
    }

    override fun toDamageInFace(state: BattleState) {
        super.toDamageInFace(state)
        state.me.hp += force
    }

    override fun executor() = CreatureExecutor()
}