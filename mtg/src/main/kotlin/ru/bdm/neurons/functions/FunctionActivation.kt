package ru.bdm.neurons.functions

import java.io.Serializable

abstract class FunctionActivation : Serializable {
    abstract fun act(x: Double): Double
    abstract fun der(x: Double): Double

    override fun toString(): String {
        return javaClass.simpleName
    }

    override fun equals(other: Any?): Boolean {
        if (other != null) {
            return javaClass == other.javaClass
        }
        return false
    }
}