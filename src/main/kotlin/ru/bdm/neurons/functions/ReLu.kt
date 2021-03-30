package ru.bdm.neurons.functions

class ReLu(val alpha: Double = 0.001) : FunctionActivation() {
    override fun act(x: Double): Double {
        return if (x >= 0) x else alpha * x
    }

    override fun der(x: Double): Double {
        return if(x >= 0) 1.0 else alpha
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReLu

        if (alpha != other.alpha) return false

        return true
    }

    override fun hashCode(): Int {
        return alpha.hashCode()
    }


}