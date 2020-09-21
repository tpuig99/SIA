package NeuralNetwork.Neurons;

import NeuralNetwork.Function.ActivationFunction;

public class OutputNeuron extends Neuron{
    public OutputNeuron(ActivationFunction function, double learnRate) {
        super(function, learnRate);
    }


    @Override
    public void calculateDelta(Double result) {
        this.delta= g.derivative(h)*(result-V);
    }

    @Override
    public void connect(Neuron n) {
    }
}
