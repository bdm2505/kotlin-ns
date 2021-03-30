package ru.bdm.neurons

import ru.bdm.neurons.functions.FunctionActivation
import ru.bdm.neurons.functions.Sigmoid
import java.io.*

object NeuronSystemLoader{
    fun load(fileName: String): NeuronSystem {
        val inputStream = ObjectInputStream(FileInputStream(fileName))
        return inputStream.readObject() as NeuronSystem
    }
}

class NeuronSystem : Serializable {
    val layers: List<Layer>
    val input: Array<INeuron>
    val output: Array<Neuron>


    constructor(funActivation: FunctionActivation,vararg sizes: Int) {
        if (sizes.size < 2)
            throw Exception("size ns < 2")
        input = createInputNeurons(sizes[0])
        layers = createLayers(sizes, input, funActivation)
        output = layers.last().output
    }
    constructor(vararg sizes: Int) : this(Sigmoid, *sizes)

    private constructor(layers: List<Layer>){
        this.layers = layers
        input = layers.first().input
        output = layers.last().output
    }

    fun work(inputs: ArrayList<Double>) : ArrayList<Double> {
        if(inputs.size != input.size)
            throw Exception("input size != inputNeurons size")
        reset()
        for(i in input.indices){
            (input[i] as InputNeuron).result = inputs[i]
        }
        return output.map { n -> n.get() } as ArrayList<Double>
    }

    private fun createInputNeurons(size: Int): Array<INeuron> {

        return Array(size) { InputNeuron() }
    }

    private fun createLayers(sizes: IntArray, inputs: Array<INeuron>, funAct: FunctionActivation): ArrayList<Layer> {
        var oldInput =  inputs
        val list = ArrayList<Layer>(sizes.size - 1)
        for(i in 1 until sizes.size) {
            val layer = Layer(sizes[i], funAct, oldInput)
            list.add(layer)
            oldInput = layer.output as Array<INeuron>
        }
        return list
    }

    fun reset(){
        for(layer in layers){
            layer.reset()
        }
    }

    fun copy():NeuronSystem{
        var oldInput = createInputNeurons(input.size)
        val list = ArrayList<Layer>(layers.size)
        for(i in layers.indices) {
            val layer = layers[i].copy(oldInput)
            list.add(layer)
            oldInput = layer.output as Array<INeuron>
        }
        return NeuronSystem(list)
    }

    override fun toString(): String {
        return "NeuronSystem($layers)"
    }


    fun save(fileName: String){
        val out = ObjectOutputStream(FileOutputStream(fileName))
        out.writeObject(this)
        out.flush()
        out.close()
    }


}