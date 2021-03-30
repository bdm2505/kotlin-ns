package ru.bdm.neurons.learn

import ru.bdm.neurons.NeuronSystem

open class NSCounterListener(val count: Int = 1000) : NSListener {
    override fun listen(ns: NeuronSystem, alg: LearnAlgorithm) {
        if (alg.iterations % count == 0)
            printData?.let { it(ns, alg) }
    }

    var printData: ((NeuronSystem, LearnAlgorithm) -> Unit)? = null
}