package ru.bdm.mtg.cards

import ru.bdm.mtg.Status

class BlackDaemon : Creature(5, 5) {

    override fun executor() = CreatureExecutor()

    init {
        status.add(Status.FLYING)
    }
}