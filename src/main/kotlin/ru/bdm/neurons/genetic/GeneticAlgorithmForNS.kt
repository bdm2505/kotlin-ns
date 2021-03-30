package ru.bdm.neurons.genetic

import ru.bdm.neurons.NeuronSystem
import ru.bdm.neurons.Rand

class GeneticAlgorithmForNS(
    val ns: NeuronSystem,
    val getScore: (NeuronSystem) -> Double,
    numberChromosomes: Int = 100,
    percentBest: Double = 0.05,
    percentMerge: Double = 0.5,
    percentMutation: Double = 0.4,
    val percentSuccessMerge: Double = 0.4,
    val percentSuccessMutation: Double = 0.1
) : GeneticAlgorithm<NeuronSystem>(numberChromosomes, percentBest, percentMerge, percentMutation) {
    override fun create(): NeuronSystem {
        return ns.copy().randomize()
    }

    override fun merge(first: NeuronSystem, second: NeuronSystem): NeuronSystem {
        val ns = first.copy()
        for((layer1, layer2) in ns.layers.zip(second.layers)){
            for((neuron1, neuron2) in layer1.output.zip(layer2.output))
                for(i in neuron1.weights.indices){
                    if(Rand.nextDouble() < percentSuccessMerge)
                        neuron1.weights[i] = neuron2.weights[i]
                }
        }
        return ns
    }

    override fun appraisal(chromosomes: List<NeuronSystem>): List<Double> {
        return chromosomes.map{ ns -> getScore(ns) }
    }

    override fun mutation(chr: NeuronSystem): NeuronSystem {
        val ns = chr.copy()
        for(layer in ns.layers)
            for(neuron in layer.output)
                for( i in neuron.weights.indices){
                    if(Rand.nextDouble() < percentSuccessMutation)
                        neuron.weights[i] += Rand.generateDouble()
                }
        return ns
    }
}