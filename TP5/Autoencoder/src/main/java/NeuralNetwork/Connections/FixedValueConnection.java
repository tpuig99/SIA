package NeuralNetwork.Connections;


import NeuralNetwork.Neurons.Neuron;

public class FixedValueConnection extends Connection {
    private double fixedValue;

    @Override
    public double getWeightedValue() {
        return fixedValue * weight;
    }

    @Override
    public void updateDeltaWeight() {
        deltaWeight += output.learningRate * output.getDelta() * fixedValue;
    }

    public void setValue(double value) {
        this.fixedValue = value;
    }

    public double getValue() {
        return fixedValue;
    }

    public FixedValueConnection(Neuron output, double value) {
        super(output);
        this.fixedValue = value;
    }
}
