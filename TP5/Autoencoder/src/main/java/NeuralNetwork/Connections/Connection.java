package NeuralNetwork.Connections;


import NeuralNetwork.Globals;
import NeuralNetwork.Neurons.Neuron;

public abstract class Connection {
    private static final double MIN_WEIGHT = -0.5;
    private static final double MAX_WEIGHT = 0.5;

    protected Neuron output;

    protected double weight;

    protected double deltaWeight = 0;

    protected double velocity = 0;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public abstract double getWeightedValue();

    public void updateWeight(double momentum) {
        velocity = momentum * velocity + (1 - momentum) * deltaWeight;
        weight += velocity;
        deltaWeight = 0;
    }

    public abstract void updateDeltaWeight();

    public Neuron getOutput() {
        return output;
    }

    protected Connection(Neuron output) {
        this.output = output;
        this.weight = MIN_WEIGHT + Globals.random.nextDouble() * (MAX_WEIGHT - MIN_WEIGHT);
    }
}
