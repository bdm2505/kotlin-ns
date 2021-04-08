package ru.bdm.neurons.functions

import kotlin.math.pow
import kotlin.math.E

object Sigmoid : FunctionActivation() {
    override fun act(x: Double): Double {
        return 1 / (1 + E.pow(-x))
    }

    override fun der(x: Double): Double {
        return x * (1 - x)
    }
}