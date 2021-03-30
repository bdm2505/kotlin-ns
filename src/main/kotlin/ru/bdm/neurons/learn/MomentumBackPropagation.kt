package ru.bdm.neurons.learn

import ru.bdm.neurons.Neuron
import ru.bdm.neurons.NeuronSystem

class MomentumBackPropagation(ns: NeuronSystem) : BackPropagation(ns) {

    val oldGrads = hashMapOf<Neuron, Array<Double>>()
    var momentumSpeedLearn = 0.3


    override fun updateWeights(neuron: Neuron) {
        neuron.apply {
            if (!oldGrads.containsKey(neuron))
                oldGrads[neuron] = Array(neuron.input.size) { 0.0 }

            val oldGrad = oldGrads[neuron]!!
            for(i in input.indices){
                val grad = input[i].get() * error
                weights[i] += speedLearn * grad + oldGrad[i] * momentumSpeedLearn
            }
        }

    }
}