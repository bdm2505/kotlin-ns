package ru.bdm.mtg.cards

import ru.bdm.mtg.Executor

interface ArchfiendsVesselInterface : CreatureInterface {

}

class ArchfiendsVesselExecutor : Executor(), ArchfiendsVesselInterface {

}

class ArchfiendsVessel() : Creature() {

    override fun executor() = ArchfiendsVesselExecutor()
}