package NeuralNetwork.Neurons;

import NeuralNetwork.Connections.Connection;
import NeuralNetwork.Connections.ConstantValueConnection;
import NeuralNetwork.Connections.NeuronConnection;
import NeuralNetwork.Functions.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    public List<NeuronConnection> output;
    public List<Connection> input;
    protected ActivationFunction g;

    protected double V;
    protected double H;

    protected double delta;

    protected double eta;

    public int layerIndex;

    public Neuron(int layerIndex, ActivationFunction g, double eta, Integer bias) {
        output = new ArrayList<>();
        input = new ArrayList<>();

        this.eta = eta;
        this.g = g;

        this.layerIndex = layerIndex;

        if(bias != null) {
            input.add(new ConstantValueConnection(this, bias));
        }
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
        for(Connection c : input) {
            val += c.getWeightedValue();
        }
        return val;
    }

    public void process() {
        this.H = getWeightedInput();
        this.V = g.evaluate(H);
    }

    public void addInput(Connection c) {
        input.add(c);
    }

    public double getDelta() {
        return delta;
    }

    public void calculateDelta(double expectedValue) {
        double otherSum = 0;

        for(NeuronConnection c : output) {
            otherSum += c.getWeight() * c.getOutput().getDelta();
        }
        delta = g.derivative(H) * otherSum;
    }
}
