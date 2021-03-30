package ru.bdm.neurons.learn

class PrinterCounterListener(count: Int = 1000) : NSCounterListener(count) {
    init {
        printData = { ns, alg ->
            println("${alg.iterations}:  testError: ${alg.absError},   minError: ${alg.minError},   averageError: ${alg.averageError}")
        }
    }
}