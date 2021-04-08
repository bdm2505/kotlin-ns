package ru.bdm.mtg

enum class Status {
    EMPTY,
    FLYING;

    fun canBlock(enemy: Set<Status>): Boolean = !enemy.contains(FLYING) || this == FLYING
}