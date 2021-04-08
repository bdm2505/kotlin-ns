package ru.bdm.mtg

enum class Phase {
    START,
    ATTACK,
    BLOCK,
    END_ATTACK,
    END;

    fun next(): Phase{
        return when(this){
            START -> ATTACK
            ATTACK -> BLOCK
            BLOCK -> END_ATTACK
            END_ATTACK -> END
            END -> START
        }
    }

}