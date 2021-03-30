package ru.bdm.neurons

import ru.bdm.neurons.functions.FunctionActivation
import java.io.Serializable



class Neuron(
    val input: Array<INeuron>,
    val weights: Array<Double>,
    val activation: FunctionActivation
) : INeuron, Serializable {

    constructor(input: Array<INeuron>, activation: FunctionActivation) :
            this(input, Array(input.size + 1) { Rand.generateDouble() }, activation)

    private var result = .0

    var isResult = false
    override var error: Double = 0.0

    override fun get(): Double {
        if (!isResult)
            work()
        return result;
    }



    fun work(): Unit {
        var sum = .0
        for (i in input.indices) {
            sum += weights[i] * input[i].get()
        }
        result = activation.act(sum + weights[weights.size - 1])
        isResult = true
    }

    fun reset() {
        isResult = false
        error = 0.0
    }


    var bias: Double
        get() {
            return weights[weights.size - 1]
        }
        set(v) {
            weights[weights.size - 1] = v
        }



    override fun toString(): String {
        return "Neuron(id=${super.toString().takeLast(4)}, input=${input.contentToString()}, weights=${weights.contentToString()}, activation=$activation, result=$result, isResult=$isResult, bias=$bias)"
    }

    fun copy(input: Array<INeuron>): Neuron {
        return Neuron(input, weights.clone(), activation)
    }

    fun randomizeWeights() {
        for(i in weights.indices){
            weights[i] = Rand.generateDouble()
        }
    }

}