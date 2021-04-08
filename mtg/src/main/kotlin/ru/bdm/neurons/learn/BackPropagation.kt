package ru.bdm.neurons.learn

import ru.bdm.neurons.Neuron
import ru.bdm.neurons.NeuronSystem
import kotlin.math.pow
import kotlin.math.sqrt

open class BackPropagation(override val ns: NeuronSystem) : LearnAlgorithm() {

    var speedLearn = 0.01
    var error = .0
    var sumError = .0
    var allCounts = 0
    var maxCounts = 2000000



    override fun learnOne(input: ArrayList<Double>, rightAnswer: ArrayList<Double>){
        ns.work(input)
        calculateErrorOutput(ns.layers.last().output, rightAnswer)
        calculateAllNeuronsError()
        updateAllWeight()
        iterations += 1
        super.learnOne(input, rightAnswer)
    }

    private fun calculateErrorOutput(neurons: Array<Neuron>, rightAnswer: ArrayList<Double>){
        if(neurons.size != rightAnswer.size)
            throw Exception("neuron output size != right answer size")
        error = .0
        for(i in neurons.indices){
            neurons[i].error = (rightAnswer[i] - neurons[i].get())
            error += (rightAnswer[i] - neurons[i].get()).pow(2)
        }
        error = sqrt(error / neurons.size)

        allCounts++
        sumError += error

        if (allCounts > maxCounts){
            allCounts = 1
            sumError = error
        }
        averageError = sumError / allCounts

        if (error < minError)
            minError = error
    }

    private fun calculateError(neuron: Neuron){
        neuron.apply {
            error *= activation.der(get())

            for(i in input.indices){
                input[i].error += weights[i] * error
            }
        }
    }

    private fun calculateAllNeuronsError(){
        val set = HashSet<Neuron>(ns.output.toList())

        while (set.isNotEmpty()){
            val curr = set.toList()
            set.clear()
            for(neuron in curr){
                calculateError(neuron)
                for(nn in neuron.input){
                    if(nn is Neuron)
                        set.add(nn)
                }
            }
        }
    }

    open fun updateWeights(neuron: Neuron){
        neuron.apply {
            for(i in input.indices){
                weights[i] += speedLearn * input[i].get() * error
            }
        }
    }

    fun updateAllWeight(){
        for(layer in ns.layers)
            for(neuron in layer.output)
                updateWeights(neuron)
    }
}