package NeuralNetwork.Connections;

import NeuralNetwork.Neurons.Neuron;

public class NeuronConnection extends Connection {
    private Neuron input;

    public NeuronConnection(Neuron input, Neuron output) {
        super(output);
        this.input = input;
    }

    public Neuron getInput() {
        return input;
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
