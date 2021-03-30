package ru.bdm.neurons

class InputNeuron(var result: Double = 0.0) : INeuron {

    override fun get(): Double {
        return result
    }

    override var error: Double
        get() = .0
        set(value) {}

    override fun toString(): String {
        return "In($result ${super.toString().takeLast(4)})"
    }
}