package ru.bdm.neurons.learn

import ru.bdm.neurons.NeuronSystem

interface NSListener {

    fun listen(ns: NeuronSystem, alg: LearnAlgorithm)
}