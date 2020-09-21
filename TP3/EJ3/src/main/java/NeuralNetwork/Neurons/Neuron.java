package NeuralNetwork.Neurons;

import NeuralNetwork.Connections.Connection;
import NeuralNetwork.Connections.FixerInnerConnection;
import NeuralNetwork.Connections.InnerConnection;
import NeuralNetwork.Function.ActivationFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Neuron {
    public List<Connection> input;
    ActivationFunction g;
    double V;
    double h;
    double delta;
    double learnRate = 0.05;
    double w0;
    double fixedValue=-1;

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
        Random random = new Random();
        w0=random.nextDouble()-0.5;
        input.add(new FixerInnerConnection(this,-1));
    }

    public void setInput(List<Connection> input) {
        this.input = input;
    }

    public void process(){
        this.h=calculateh();
        this.V=calculateV();
    }

    private double calculateV() {
        return g.calculate(h);
    }

    private double calculateh() {
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
