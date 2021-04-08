package ru.bdm.neurons

import java.io.Serializable

interface INeuron : Serializable {
    fun get():Double
    var error: Double
}