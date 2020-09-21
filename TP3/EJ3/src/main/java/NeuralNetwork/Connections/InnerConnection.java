package NeuralNetwork.Connections;

import NeuralNetwork.Neurons.Neuron;

import java.util.Random;

public class InnerConnection extends Connection{
    Neuron input;

    public InnerConnection(Neuron input, Neuron output) {
        super(output);
        this.input = input;
    }

    public Neuron getInput() {
        return input;
    }


    @Override
    double getValue() {
        return input.getV();
    }

    @Override
    public double getForHValue() {
        return input.getV()*w;
    }

    ;
}
