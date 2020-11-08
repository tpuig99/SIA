package neuralNetwork.Connections;

import neuralNetwork.Neurons.Neuron;

import java.util.Random;

public abstract class Connection {
    Neuron output;
    double w;
    double lastDelta = 0;
    final static double MIN =- 1;
    final static double MAX = 1;

    public Connection(Neuron output) {
        this.output = output;
        Random random = new Random();
        w=(MAX-MIN)* random.nextDouble()+MIN;
    }

    public Neuron getOutput() {
        return output;
    }

    public double getW() {
        return w;
    }

    public void updateW(double momentum){
        lastDelta = lastDelta * momentum + (1 - momentum) * (output.getDelta()*getValue() * output.getLearnRate());
        w+=lastDelta;
    };
    abstract double getValue();
    abstract public double getForHValue();
}
