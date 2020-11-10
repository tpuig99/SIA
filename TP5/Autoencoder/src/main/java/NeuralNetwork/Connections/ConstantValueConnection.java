package NeuralNetwork.Connections;


import NeuralNetwork.Neurons.Neuron;

public class ConstantValueConnection extends Connection {
    private final double value;

    @Override
    public double getWeightedValue() {
        return this.value * weight;
    }

    @Override
    public void updateDeltaWeight() {
        deltaWeight += output.eta * output.getDelta() * value;
    }

    public ConstantValueConnection(Neuron output, double value) {
        super(output);
        this.value = value;
    }
}
