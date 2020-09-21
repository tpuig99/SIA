package NeuralNetwork.Connections;

import NeuralNetwork.Neurons.Neuron;

import java.util.Random;

public abstract class Connection {
    Neuron output;
    double w;

    public Connection(Neuron output) {
        this.output = output;
        Random random = new Random();
        w=random.nextDouble()-0.5;
    }

    public Neuron getOutput() {
        return output;
    }

    public double getW() {
        return w;
    }

    public void updateW(){
        w+=output.getLearnRate()*output.getDelta()*getValue();
    };
    abstract double getValue();
    abstract public double getForHValue();
}
