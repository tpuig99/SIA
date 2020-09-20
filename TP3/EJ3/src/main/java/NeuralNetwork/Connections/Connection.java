package NeuralNetwork.Connections;

import NeuralNetwork.Neurons.Neuron;

import java.util.Random;

public class Connection {
    Neuron input;
    Neuron output;
    double w;

    public Connection(Neuron input, Neuron output) {
        this.input = input;
        this.output = output;
        Random random = new Random();
        w=random.nextDouble()-0.5;
    }



    public Neuron getInput() {
        return input;
    }

    public Neuron getOutput() {
        return output;
    }

    public double getW() {
        return w;
    }

    public void updateW(){
        w+= output.getLearnRate()*output.getDelta()*output.getV();
    };
}
