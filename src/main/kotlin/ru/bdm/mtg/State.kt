package ru.bdm.mtg

import ru.bdm.mtg.Phase.*

typealias State = Pair<StatePlayer, StatePlayer>

fun State.nextBattleState(): State {
    me.phase = me.phase.next()
    enemy.phase = enemy.phase.next()
    return this
}
fun State.swap(): State = Pair(enemy, me)

fun State.nextTurn(): State {
    return when (first.phase) {
        START -> nextBattleState().clone()
        ATTACK -> nextBattleState().swap().clone()
        BLOCK -> nextBattleState().swap().clone()
        END_ATTACK -> nextBattleState().clone()
        END -> {
            val res = nextBattleState().clone()
            res.me.mana.clear()
            res.me.numberCourse += 1
            res.me.isLandPlayable = false
            res.swap()
        }
    }
}

val State.me
    get() = first


val State.enemy
    get() = second

fun stateEmpty() = State(StatePlayer(), StatePlayer())


fun State.getDifference(next: State): Pair<Difference, Difference> {
    return Pair(first.getDifference(next.first), second.getDifference(next.second))
}
