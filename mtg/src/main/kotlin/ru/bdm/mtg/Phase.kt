package ru.bdm.mtg

enum class Phase {
    START,
    BLOCK,
    END_ATTACK,
    END;

    fun next(): Phase{
        return when(this){
            START -> BLOCK
            BLOCK -> END_ATTACK
            END_ATTACK -> END
            END -> START
        }
    }

}