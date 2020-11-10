package NeuralNetwork.Neurons;

import NeuralNetwork.Connections.Connection;
import NeuralNetwork.Connections.FixedValueConnection;
import NeuralNetwork.Connections.NeuronConnection;
import NeuralNetwork.Functions.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    public List<NeuronConnection> output;
    public List<Connection> input;
    protected ActivationFunction g;

    protected double V;
    protected double h;

    protected double delta;

    public double learningRate = 0.05;

    public int layerIndex;

    public Neuron(int layerIndex, ActivationFunction g, double learningRate, Integer bias) {
        output = new ArrayList<>();
        input = new ArrayList<>();

        this.learningRate = learningRate;
        this.g = g;

        this.layerIndex = layerIndex;

        //bias //TODO valor?
        if(bias != null)
            input.add(new FixedValueConnection(this, bias));
    }

    public double getValue() {
        return V;
    }

    public void setValue(double value) {
        this.V = value;
    }

    public void connectTo(Neuron other) {
        NeuronConnection c = new NeuronConnection(this, other);
        output.add(c);
        other.addInput(c);
    }

    private double getWeightedInput() {
        double val = 0;
        for(Connection c : input)
            val += c.getWeightedValue();

        return val;
    }

    public void process() {
        this.h = getWeightedInput();
        this.V = g.evaluate(h);
    }

    public void addInput(Connection c) {
        input.add(c);
    }

    public void addOutput(NeuronConnection c) {
        output.add(c);
    }

    public double getDelta() {
        return delta;
    }

    public void calculateDelta(double expectedValue) {
        double otherSum = 0;

        //TODO y las FixedValueCollections? No juegan?
        for(NeuronConnection c : output)
            otherSum += c.getWeight() * c.getOutput().getDelta();

        delta = g.evaluateDerivative(h) * otherSum;
    }
}
