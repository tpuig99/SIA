package NeuralNetwork.Neurons;

import NeuralNetwork.Functions.ActivationFunction;

public class OutputNeuron extends Neuron {
    public OutputNeuron(int layerIndex, ActivationFunction g, double learningRate, Integer bias) {
        super(layerIndex, g, learningRate, bias);
    }

    @Override
    public void calculateDelta(double expectedValue) {
        delta = (expectedValue - V) * g.evaluateDerivative(h);
    }
}
