package NeuralNetwork.Neurons;

import NeuralNetwork.Function.ActivationFunction;

public class OutputNeuron extends Neuron{
    public OutputNeuron(ActivationFunction function, double learnRate) {
        super(function, learnRate);
    }


    @Override
    public double calculateDelta(Double result) {
        return g.derivative(h)*(result-V);
    }

    @Override
    public void connect(Neuron n) {
    }
}
