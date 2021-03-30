package ru.bdm.neurons.functions

import ru.bdm.neurons.functions.FunctionActivation

object Linear : FunctionActivation() {
    override fun act(x: Double): Double {
        return x
    }

    override fun der(x: Double): Double {
        return 1.0
    }
}