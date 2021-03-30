package ru.bdm.neurons

import ru.bdm.neurons.functions.FunctionActivation
import java.io.Serializable

open class Layer : Serializable {
    val input: Array<INeuron>
    val output: Array<Neuron>

    constructor(input: Array<INeuron>, output: Array<Neuron>){
        this.input = input
        this.output = output
    }

    constructor(countNeurons: Int, activation: FunctionActivation, input: Array<INeuron>) {
        this.input = input
        this.output = Array(countNeurons) { Neuron(input, activation) }
    }


    fun work(){
        for(neuron in output){
            neuron.work()
        }
    }
    open fun reset(){
        for(neuron in output){
            neuron.reset()
        }
    }

    fun copy(input: Array<INeuron>): Layer {
        return Layer(input, Array(output.size) { n -> output[n].copy(input) })
    }

    override fun toString(): String {
        return "Layer(input=${input.contentToString()}, output=${output.contentToString()})"
    }


}