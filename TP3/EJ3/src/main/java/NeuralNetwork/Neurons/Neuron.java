package NeuralNetwork.Neurons;

import NeuralNetwork.Connections.Connection;
import NeuralNetwork.Function.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron {
    public List<Connection> input;
    ActivationFunction g;
    double V;
    double h;
    double delta;
    double learnRate = 0.05;
    double w0=1;

    public double getLearnRate() {
        return learnRate;
    }

    public void setV(double v) {
        V = v;
    }

    public Neuron(ActivationFunction function, double learnRate) {
        this.learnRate = learnRate;
        this.g = function;
        input = new ArrayList<>();
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
        double aux=0;
        for (Connection c: input) {
            c.getInput().process();
            aux+=c.getW()*c.getInput().V;
        }
        //PREGUNTAR SI W0 SE SUMA
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

    public abstract double calculateDelta(Double r);
    public abstract void connect(Neuron n);

}
