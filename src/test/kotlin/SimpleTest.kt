import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.bdm.neurons.*
import ru.bdm.neurons.functions.Linear
import ru.bdm.neurons.functions.Sigmoid




class SimpleTest {

    @Test
    fun testOnePlusOne() {
        assertEquals(1 + 1, 2)
    }

    @Test
    fun zeroNeuronWithSigmoid() {
        val n = Neuron(arrayOf(InputNeuron(.0), InputNeuron(.0)), Sigmoid)
        n.bias = .0
        assertEquals(n.get(), 0.5)
    }
    @Test
    fun onesNeuronWithSigmoid() {
        val n = Neuron(arrayOf(InputNeuron(.1), InputNeuron(.1)), arrayOf(10.0, 10.0, -2.0), Sigmoid)
        assertEquals(n.get(), 0.5)
    }

    @Test
    fun zeroLayerWithSigmoid() {
        val layer = Layer(2, Sigmoid, arrayOf(InputNeuron(.0), InputNeuron(.0)))
        for(neuron in layer.output){
            neuron.bias = 0.0
        }
        val res = layer.output.map { neuron -> neuron.get() }
        assertEquals(res, listOf(0.5, 0.5))
    }

    @Test
    fun onesNeuronSystemWithSigmoid() {
        val ns = NeuronSystem(2, 2)
        for(neuron in ns.layers.last().output){
            neuron.bias = 0.0
        }
        val res = ns.work(arrayListOf(0.0, 0.0))
        assertEquals(res, listOf(0.5, 0.5))
    }

    @Test
    fun linearNeuronTest(){
        val n = Neuron(arrayOf(InputNeuron(1.0), InputNeuron(.0)), Linear)
        n.bias = .0
        assertEquals(n.get(), n.weights[0])
        (n.input[0] as InputNeuron).result = 0.0
        n.reset()
        assertEquals(n.get(), 0.0)
    }

    @Test
    fun nsLoadAndSave(){
        val fileName = "testNSSave.obj"
        val ns1 = NeuronSystem(3,2,1)
        ns1.save(fileName)
        val ns2 = NeuronSystemLoader.load(fileName)
        for((layer1, layer2) in ns1.layers.zip(ns2.layers)){
            for((neuron1, neuron2) in layer1.output.zip(layer2.output)){
                assert(neuron1.weights.contentEquals(neuron2.weights))
                assertEquals(neuron1.bias, neuron2.bias)
                assertEquals(neuron1.activation, neuron2.activation)
            }
        }
    }

    @Test
    fun cloneNS(){
        val ns = NeuronSystem(3,2,1)
        val ns2 = ns.copy()
        assert(ns !== ns2)
        assert(ns.layers[0].output[0] !== ns2.layers[0].output[0])
        assert(ns.layers[0] !== ns2.layers[0])
        assert(ns.input[0] !== ns2.input[0])
    }

}