package NeuralNetwork.Connections;

import NeuralNetwork.Neurons.Neuron;

public class NeuronConnection extends Connection {
    private final Neuron input;

    public NeuronConnection(Neuron input, Neuron output) {
        super(output);
        this.input = input;
    }

    @Override
    public double getWeightedValue() {
        return input.getValue() * weight;
    }

    @Override
    public void updateDeltaWeight() {
        deltaWeight += output.learningRate * output.getDelta() * input.getValue();
    }
}
