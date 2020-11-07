package neuralNetwork.Neurons;

import neuralNetwork.Connections.Connection;
import neuralNetwork.Connections.FixerInnerConnection;
import neuralNetwork.Function.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron {
    public List<Connection> input;
    ActivationFunction g;
    double V;
    double h;
    double delta;
    double learnRate;

    public double getLearnRate() {
        return learnRate;
    }

    public void setV(double v) {
        this.V = v;
    }

    public Neuron(ActivationFunction function, double learnRate) {
        this.learnRate = learnRate;
        this.g = function;
        input = new ArrayList<>();
        input.add(new FixerInnerConnection(this,-1));
    }

    public void setInput(List<Connection> input) {
        this.input = input;
    }

    public void process(){
        this.h=calculateH();
        this.V=calculateV();
    }

    private double calculateV() {
        return g.calculate(h);
    }

    private double calculateH() {
        double aux=0.0;
        for (Connection c: input) {
            aux+=c.getForHValue();
        }
        return aux;
    }

    public double getV() {
        return V;
    }

    public double getH() {
        return h;
    }

    public double getDelta() {
        return delta;
    }

    public abstract void calculateDelta(Double r);
    public abstract void connect(Neuron n);

}
