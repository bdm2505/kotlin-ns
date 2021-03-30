package ru.bdm.neurons

import kotlin.random.Random

object Rand {
    val rand = Random(System.currentTimeMillis())
    fun nextDouble(): Double {
        return rand.nextDouble() * 2 - 1
    }
    fun nextInt(max:Int): Int{
        return rand.nextInt(max)
    }
}