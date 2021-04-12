package ru.bdm.mtg.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Card
import ru.bdm.mtg.CardInterface
import ru.bdm.mtg.Executor
import ru.bdm.mtg.Tag

interface InstantInterface : CardInterface {
    override fun canPlay(): Boolean {
        return inHand() && enoughMana()
    }

    fun meCreatures(): List<Int> = me.battlefield.filter { me(it) is Creature }

    fun getActions(): List<() -> Unit> = listOf()
}

open class InstantExecutor : Executor(), InstantInterface {

    init {
        any(this::canPlay) {
            getActions()
        }
    }
}

@Serializable
@SerialName("Instant")
open class Instant() : Card() {
    init {
        tag(Tag.INSTANT)
    }

    override fun executor(): Executor = InstantExecutor()
}